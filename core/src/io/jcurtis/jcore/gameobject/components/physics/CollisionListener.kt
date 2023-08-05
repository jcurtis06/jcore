package io.jcurtis.jcore.gameobject.components.physics

import io.jcurtis.jcore.gameobject.GameObject

/**
 * Base interface for all collision listeners.
 * All collide-able objects must implement this interface.
 *
 * @property onEnter Called when a collision begins.
 * @property onExit Called when a collision ends.
 * @property onStay Called every frame while a collision is ongoing.
 */
interface CollisionListener {
    var onEnter: ((GameObject) -> Unit)
    var onExit: ((GameObject) -> Unit)
    var onStay: ((GameObject) -> Unit)
}