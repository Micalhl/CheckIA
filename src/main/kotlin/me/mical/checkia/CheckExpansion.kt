package me.mical.checkia

import dev.lone.itemsadder.api.CustomStack
import org.bukkit.OfflinePlayer
import taboolib.platform.compat.PlaceholderExpansion

/**
 * @author xiaomu
 * @since 2022/9/9 13:55
 */
object CheckExpansion : PlaceholderExpansion {

    override val identifier: String
        get() = "checkia"

    override fun onPlaceholderRequest(player: OfflinePlayer?, args: String): String {
        if (!args.contains("_")) {
            return ""
        }
        player ?: return ""
        if (!player.isOnline) {
            return ""
        }
        val options = args.split("_")
        when (options[0].lowercase()) {
            "get" -> {
                if (options.size != 2) {
                    return ""
                }
                val user = player.player!!
                val id = options[1]
                var i = 0
                user.inventory.contents.filterNotNull().filter { it.isSimilar(CustomStack.getInstance(id)?.itemStack) }.forEach { i += it.amount }
                return i.toString()
            }
            "has" -> {
                if (options.size != 3) {
                    return ""
                }
                val user = player.player!!
                val namespacedKey = options[1]
                try {
                    val amount = options[2].toInt()
                    var i = 0
                    user.inventory.contents.filterNotNull().filter { it.isSimilar(CustomStack.getInstance(namespacedKey)?.itemStack) }.forEach { i += it.amount }
                    return (i >= amount).toString()
                } catch (e: Throwable) {
                    return ""
                }
            }
        }
        return ""
    }
}