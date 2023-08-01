package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import io.jcurtis.jcore.core.Core
import kotlin.math.abs
import kotlin.math.roundToInt

class Rigidbody: Component() {
    var velocity = Vector2(0f, 0f)

    private var collider: BoxCollider? = null

    private var collidingX = false
    private var collidingY = false

    override fun init() {
        collider = gameObject.getComponent<BoxCollider>()

        if (collider == null) {
            Logger("Rigidbody", Logger.DEBUG).debug("Rigidbody component requires a BoxCollider component")
        }
    }

    override fun update(delta: Float) {
    }

    private fun applyVelocity() {
        if (velocity.x > 0) {
            // right
            for (i in 0..velocity.x.roundToInt()) {
                val collision = checkCollisionsAt(Vector2(transform.position.x + 1, transform.position.y))

                if (collision != null) {
                    collidingX = true
                    return
                }

                collidingX = false
                transform.position.x += 1
            }
        }

        if (velocity.x < 0) {
            // left
            for (i in 0..abs(velocity.x.roundToInt())) {
                val collision = checkCollisionsAt(Vector2(transform.position.x - 1, transform.position.y))

                if (collision != null) {
                    collidingX = true
                    return
                }

                collidingX = false
                transform.position.x -= 1
            }
        }

        if (velocity.y > 0) {
            // up
            for (i in 0..velocity.y.roundToInt()) {
                val collision = checkCollisionsAt(Vector2(transform.position.x, transform.position.y + 1))

                if (collision != null) {
                    collidingY = true
                    return
                }

                collidingY = false
                transform.position.y += 1
            }
        }

        if (velocity.y < 0) {
            // down
            for (i in 0..abs(velocity.y.roundToInt())) {
                val collision = checkCollisionsAt(Vector2(transform.position.x, transform.position.y - 1))

                if (collision != null) {
                    transform.y = collision.transform.position.y + collider!!.rectangle.height
                    collidingY = true
                    return
                }

                collidingY = false
                transform.position.y -= 1
            }
        }
    }

    private fun checkCollisionsAt(position: Vector2): BoxCollider? {
        val tempCollider = Rectangle(position.x, position.y, collider!!.rectangle.width, collider!!.rectangle.height)

        Core.colliders.forEach { c ->
            if (c.rectangle.overlaps(tempCollider) && c != collider) {
                return c
            }
        }

        return null
    }

    fun moveAndSlide(velocity: Vector2) {
        applyVelocity()

        if (collidingX) {
            println("colliding x")
            velocity.set(0f, velocity.y)
        } else if (collidingY) {
            println("colliding y")
            velocity.set(velocity.x, 0f)
        }
    }
}