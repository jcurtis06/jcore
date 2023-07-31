package io.jcurtis.jcore.test

import io.jcurtis.jcore.core.modules.ModuleCore

class JCore : ModuleCore() {
    override fun init() {
        attach(Main())
    }
}
