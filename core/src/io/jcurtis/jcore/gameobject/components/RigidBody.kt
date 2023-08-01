package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core

class RigidBody: Component() {
    var velocity = Vector2(0f, 0f)

    private lateinit var collider: BoxCollider

    private var isOnFloor = false
    private var isOnWall = Pair(false, false)

    override fun update(delta: Float) {
    }

    override fun init() {
        collider = gameObject.getComponent<BoxCollider>()!!
    }

    fun moveAndSlide() {
        val collidesX = checkCollisionsAt(transform.position.cpy().add(velocity.x, 0f))
        val collidesY = checkCollisionsAt(transform.position.cpy().add(0f, velocity.y))
        val collidesXY = checkCollisionsAt(transform.position.cpy().add(velocity.x, velocity.y))

        if (collidesX.isNotEmpty()) {
            val box = collidesX[0]
            if (velocity.x > 0)
                transform.position.x = box.getLeft() - collider.width
            else
                transform.position.x = box.getRight()
            velocity.x = 0f
        }

        if (collidesY.isNotEmpty()) {
            val box = collidesY[0]
            if (velocity.y > 0)
                // up
                transform.position.y = box.getBottom() - collider.height
            else
                // down
                transform.position.y = box.getTop()
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

        transform.position.add(velocity)
    }

    private fun checkCollisionsAt(position: Vector2): List<BoxCollider> {
        collider.rectangle.x = position.x
        collider.rectangle.y = position.y

        return Core.colliders.filter { it.rectangle != collider.rectangle && it.rectangle.overlaps(collider.rectangle) }
    }
}