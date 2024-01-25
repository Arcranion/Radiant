package arcranion.radiant.util.brigadier

import arcranion.radiant.extensions.brigadier.beginModule
import arcranion.radiant.extensions.brigadier.endModule
import arcranion.radiant.module.RadiantModule
import com.mojang.brigadier.Command
import com.mojang.brigadier.RedirectModifier
import com.mojang.brigadier.SingleRedirectModifier
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.mojang.brigadier.tree.CommandNode
import java.util.concurrent.CompletableFuture
import java.util.function.Predicate

@BrigadierDsl
sealed class BrigadierBaseArgumentBuilder<
        T,
        B: ArgumentBuilder<T, B>,
>(
    val builder: B,
    private val module: RadiantModule? = null
) {
    fun requires(predicate: Predicate<T>) {
        builder.requires(predicate)
    }

    fun requires(predicate: (T) -> Boolean?) {
        builder.requires { predicate(it) ?: false }
    }

    fun literal(name: String, vararg aliases: String, setup: @BrigadierDsl BrigadierLiteralArgumentBuilder<T>.() -> Unit = {}) {
        val mainLiteralArgument =
            BrigadierLiteralArgumentBuilder(LiteralArgumentBuilder.literal<T>(name))
                .apply(setup)
                .builder
                .build()

        builder.then(mainLiteralArgument)
        for (alias in aliases) {
            val redirectLiteralArgument = BrigadierLiteralArgumentBuilder(LiteralArgumentBuilder.literal<T>(alias))
            redirectLiteralArgument.redirect(mainLiteralArgument)

            builder.then(redirectLiteralArgument.build())
        }

    }

    fun <A> argument(name: String, type: ArgumentType<A>, setup: @BrigadierDsl BrigadierRequiredArgumentBuilder<T, A>.() -> Unit = {}) {
        builder.then(
            BrigadierRequiredArgumentBuilder(RequiredArgumentBuilder.argument<T, A>(name, type)).apply(setup).builder
        )
    }

    fun redirect(target: CommandNode<T>, modifier: SingleRedirectModifier<T>? = null) {
        builder.redirect(target, modifier)
    }

    fun fork(target: CommandNode<T>, modifier: RedirectModifier<T>) {
        builder.fork(target, modifier)
    }

    fun forward(target: CommandNode<T>, modifier: RedirectModifier<T>? = null, fork: Boolean = false) {
        builder.forward(target, modifier, fork)
    }

    fun then(argument: CommandNode<T>) {
        builder.then(argument)
    }

    fun then(argument: ArgumentBuilder<T, *>) {
        builder.then(argument)
    }

    fun executes(command: @BrigadierDsl CommandContext<T>.() -> Int) {
        builder.executes(buildExecutor(command))
    }

    fun runs(command: @BrigadierDsl CommandContext<T>.() -> Unit) {
        builder.executes {
            buildExecutor(command)(it)

            Command.SINGLE_SUCCESS
        }
    }

    fun build(): CommandNode<T> {
        return builder.build()
    }

    private fun <R> buildExecutor(command: CommandContext<T>.() -> R): (CommandContext<T>) -> R {
        if(module == null) {
            return command
        } else {
            return { ctx: CommandContext<T> ->
                ctx.beginModule(module)
                val result = command(ctx)
                ctx.endModule()

                result
            }
        }
    }
}

@BrigadierDsl
class BrigadierLiteralArgumentBuilder<T>(
    builder: LiteralArgumentBuilder<T>,
    module: RadiantModule? = null
): BrigadierBaseArgumentBuilder<T, LiteralArgumentBuilder<T>>(builder, module)

class BrigadierRequiredArgumentBuilder<C, T>(
    builder: RequiredArgumentBuilder<C, T>,
    module: RadiantModule? = null
): BrigadierBaseArgumentBuilder<C, RequiredArgumentBuilder<C, T>>(builder, module) {
    fun suggests(provider: @BrigadierDsl SuggestionProvider<C>) {
        builder.suggests(provider)
    }

    fun suggests(provider: @BrigadierDsl (context: CommandContext<C>, builder: SuggestionsBuilder) -> CompletableFuture<Suggestions>) {
        builder.suggests(provider)
    }
}