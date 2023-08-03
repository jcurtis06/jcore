package io.jcurtis.jcore.gameobject.components

import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.physics.Transform

/**
 * A component that can be added to a game object to add functionality.
 * @property transform The transform component of the game object.
 * @property gameObject The game object that the component is attached to.
 */
abstract class Component() {
    lateinit var transform: Transform
    lateinit var gameObject: GameObject

    abstract fun update(delta: Float)

    abstract fun init()

    open fun postInit() {

    }
}