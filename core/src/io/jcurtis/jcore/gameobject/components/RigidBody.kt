package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core

class RigidBody: Component() {
    var velocity = Vector2(0f, 0f)

    private lateinit var collider: BoxCollider

    class CollidingDirection {
        var down = false
        var up = false
        var left = false
        var right = false
        var none = true
    }

    val collidingDirection = CollidingDirection()

    override fun update(delta: Float) {
    }

    override fun init() {
        collider = gameObject.getComponent<BoxCollider>()!!
    }

    fun moveAndSlide(velocity: Vector2) {
        collidingDirection.down = false
        collidingDirection.up = false
        collidingDirection.left = false
        collidingDirection.right = false
        collidingDirection.none = true

        val collidesX = checkCollisionsAt(transform.position.cpy().add(velocity.x, 0f))
        val collidesY = checkCollisionsAt(transform.position.cpy().add(0f, velocity.y))
        val collidesXY = checkCollisionsAt(transform.position.cpy().add(velocity.x, velocity.y))

        if (collidesX.isNotEmpty()) {
            val box = collidesX[0]
            if (velocity.x > 0) {
                transform.position.x = box.getLeft() - collider.width
                collidingDirection.right = true
            }
            else {
                transform.position.x = box.getRight()
                collidingDirection.left = true
            }
            velocity.x = 0f
        }

        if (collidesY.isNotEmpty()) {
            val box = collidesY[0]
            if (velocity.y > 0) {
                transform.position.y = box.getBottom() - collider.height
                collidingDirection.up = true
            }
            else {
                transform.position.y = box.getTop()
                collidingDirection.down = true
            }
            velocity.y = 0f
        }

        if (collidesXY.isNotEmpty() && collidesX.isEmpty() && collidesY.isEmpty()) {
            val box = collidesXY[0]
            if (velocity.x > 0)
                transform.position.x = box.getLeft() - collider.width
            else
                transform.position.x = box.getRight()
            if (velocity.y > 0)
                transform.position.y = box.getBottom() - collider.height
            else
                transform.position.y = box.getTop()
            velocity.x = 0f
            velocity.y = 0f
        }

        collidingDirection.none = !(collidingDirection.down || collidingDirection.up || collidingDirection.left || collidingDirection.right)

        transform.position.add(velocity)
    }

    private fun checkCollisionsAt(position: Vector2): List<BoxCollider> {
        collider.rectangle.x = position.x
        collider.rectangle.y = position.y

        return Core.colliders.filter { it.rectangle != collider.rectangle &&
                                        it.layer == collider.layer &&
                                        it.rectangle.overlaps(collider.rectangle) }
    }
}