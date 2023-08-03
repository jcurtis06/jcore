package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Rectangle
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component

/**
 * A component that allows a GameObject to interact with other objects.
 * @property isTrigger Whether the collider is a trigger, meaning it will not stop the GameObject from moving.
 * @property enterCallback A callback that is called when the GameObject enters a collision.
 * @property stayCallback A callback that is called when the GameObject stays in a collision.
 * @property exitCallback A callback that is called when the GameObject exits a collision.
 * @property layer The layer that the collider is on. Colliders on the same layer will not collide with each other.
 * @property width The width of the collider.
 * @property height The height of the collider.
 * @property rectangle The rectangle that represents the collider.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class BoxCollider : Component() {
    var isTrigger = false

    var enterCallback: ((BoxCollider) -> Unit)? = null
    var stayCallback: ((BoxCollider) -> Unit)? = null
    var exitCallback: ((BoxCollider) -> Unit)? = null

    var layer = 0


    var width = 0f
        set(value) {
            field = value
            rectangle.width = value
        }

    var height = 0f
        set(value) {
            field = value
            rectangle.height = value
        }

    var rectangle = Rectangle()


    private var collisions = mutableListOf<BoxCollider>()
    private var newCollisions = mutableListOf<BoxCollider>()

    override fun init() {
        rectangle.setPosition(transform.position)
        rectangle.width = width
        rectangle.height = height

        Core.colliders.add(this)
    }

    fun getTop(): Float {
        return transform.position.y + height
    }

    fun getBottom(): Float {
        return transform.position.y
    }

    fun getLeft(): Float {
        return transform.position.x
    }

    fun getRight(): Float {
        return transform.position.x + width
    }

    override fun update(delta: Float) {
        rectangle.setPosition(transform.position)

        newCollisions.clear()

        Core.colliders.forEach { collider ->
            if (collider != this && rectangle.overlaps(collider.rectangle)) {
                newCollisions.add(collider)
                if (!collisions.contains(collider)) {
                    enterCallback?.let { it(collider) }
                } else {
                    stayCallback?.let { it(collider) }
                }
            }
        }

        collisions = collisions.filter { collider ->
            if (!newCollisions.contains(collider)) {
                exitCallback?.invoke(collider)
                false
            } else {
                true
            }
        }.toMutableList()

        collisions.addAll(newCollisions)
    }
}