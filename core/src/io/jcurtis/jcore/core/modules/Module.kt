package io.jcurtis.jcore.core.modules

import com.badlogic.gdx.InputAdapter

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Module: InputAdapter() {
    open fun update() {

    }

    open fun init() {

    }

    open fun preInit() {

    }

    open fun pause() {

    }

    open fun resume() {

    }

    open fun dispose() {

    }

    open fun resize() {

    }

    open fun resize(width: Int, height: Int) {
        resize()
    }
}