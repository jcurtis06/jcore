package io.jcurtis.jcore.test

import io.jcurtis.jcore.core.modules.ModuleCore

class JCore : ModuleCore() {
    override fun init() {
        println("hello world!")

        attach(Main())
    }
}
