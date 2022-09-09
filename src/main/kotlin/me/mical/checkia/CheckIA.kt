package me.mical.checkia

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info

object CheckIA : Plugin() {

    override fun onEnable() {
        info("Successfully running ExamplePlugin!")
    }
}