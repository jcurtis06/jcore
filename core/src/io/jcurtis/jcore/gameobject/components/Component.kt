package io.jcurtis.jcore.gameobject.components

import io.jcurtis.jcore.gameobject.GameObject

abstract class Component {
    lateinit var transform: Transform
    lateinit var gameObject: GameObject

    abstract fun update(delta: Float)

    abstract fun init()

    open fun postInit() {

    }
}