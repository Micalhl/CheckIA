package me.mical.checkia

import dev.lone.itemsadder.api.CustomStack
import org.bukkit.Bukkit
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.common.platform.function.onlinePlayers

/**
 * @author xiaomu
 * @since 2022/9/9 13:56
 */
object CheckCommands {

    @Awake(LifeCycle.ENABLE)
    fun init() {
        command("iatake", permission = "starryskycore.command.iatake") {
            dynamic("Player") {
                suggestion<ProxyCommandSender> { _, _ ->
                    onlinePlayers().map { it.name }
                }
                dynamic("NamespacedKey") {
                    dynamic("Amount") {
                        execute<ProxyCommandSender> { _, context, _ ->
                            val user = Bukkit.getPlayerExact(context.argument(-2)) ?: return@execute
                            val namespacedKey = context.argument(-1)
                            val amount = context.argument(0).toInt()
                            var i = amount
                            val items = user.inventory.contents.clone().filterNotNull().filter { it.isSimilar(
                                CustomStack.getInstance(namespacedKey)?.itemStack) }
                            for (it in items) {
                                when {
                                    it.amount > i -> {
                                        it.amount -= i
                                        break
                                    }
                                    it.amount == i -> {
                                        it.amount = 0
                                        break
                                    }
                                    it.amount < amount -> {
                                        i -= it.amount
                                        it.amount = 0
                                        continue
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}