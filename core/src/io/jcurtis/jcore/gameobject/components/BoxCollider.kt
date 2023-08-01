package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Rectangle
import io.jcurtis.jcore.core.Core


class BoxCollider: Component() {
    var isTrigger = false
    var enterCallback: ((BoxCollider) -> Unit)? = null
    var stayCallback: ((BoxCollider) -> Unit)? = null
    var exitCallback: ((BoxCollider) -> Unit)? = null

    var rectangle = Rectangle()

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


    private var collisions = mutableListOf<BoxCollider>()
    private var newCollisions = mutableListOf<BoxCollider>()

    init {
        Core.colliders.add(this)
        rectangle.width = width
        rectangle.height = height
    }

    override fun init() {
        return
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
        rectangle.x = transform.position.x
        rectangle.y = transform.position.y

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

        collisions.removeAll { collider ->
            if (!newCollisions.contains(collider)) {
                exitCallback?.let { it(collider) }
                true
            } else {
                false
            }
        }

        collisions.addAll(newCollisions)
    }
}