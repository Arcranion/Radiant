package arcranion.radiant.extensions.brigadier

import arcranion.radiant.extensions.reflection.privateFieldValue
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.context.ParsedArgument
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import com.sun.jdi.connect.Connector.StringArgument
import net.minecraft.commands.CommandSourceStack
import org.bukkit.command.CommandSender
import java.lang.IllegalArgumentException

fun <T> CommandNode<T>.removeChild(name: String) {
    val children = this.privateFieldValue<MutableMap<String, CommandNode<T>>>("children")
    val literals = this.privateFieldValue<MutableMap<String, LiteralCommandNode<T>>>("literals")
    val arguments = this.privateFieldValue<MutableMap<String, ArgumentCommandNode<T, *>>>("arguments")

    children -= name
    literals -= name
    arguments -= name
}

fun CommandDispatcher<*>.unregister(name: String) {
    this.root.removeChild(name)
}

fun <T> CommandDispatcher<T>.register(node: LiteralCommandNode<T>) {
    this.root.addChild(node)
}

inline fun <reified T> CommandContext<*>.getArgument(name: String): T {
    return getArgument(name, T::class.java)
}

inline fun <reified T> CommandContext<*>.getArgumentOrNull(name: String): T? {
    val arguments = this.privateFieldValue<MutableMap<String, ParsedArgument<*, *>>>("arguments")

    val argument = arguments[name] ?: return null

    return argument as? T
        ?: throw IllegalArgumentException("Argument '${name}' is defined as ${argument.result::class.simpleName}, not ${T::class}")
}

fun CommandContext<*>.getString(name: String): String = getArgument<String>(name)
fun CommandContext<*>.getInteger(name: String): Int = getArgument<Int>(name)
fun CommandContext<*>.getFloat(name: String): Float = getArgument<Float>(name)
fun CommandContext<*>.getDouble(name: String): Double = getArgument<Double>(name)
fun CommandContext<*>.getLong(name: String): Long = getArgument<Long>(name)
fun CommandContext<*>.getBool(name: String): Boolean = getArgument<Boolean>(name)

fun CommandContext<*>.getSplitString(name: String, delimiters: String = " "): List<String> = getString(name).split(delimiters)