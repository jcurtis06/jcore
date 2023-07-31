package io.jcurtis.jcore.gameobject.components

import io.jcurtis.jcore.gameobject.GameObject

abstract class Component(gameObject: GameObject) {
    lateinit var transform: Transform

    abstract fun update(delta: Float)

    abstract fun init()
}