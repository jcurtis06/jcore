package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.math.Round
import kotlin.math.abs
import kotlin.math.min

class RigidBody : Component() {
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

    fun moveAndSlide() {
        val oldPos = Vector2(transform.position)
        var newPos = oldPos.cpy().add(velocity.x, 0f)  // Check horizontal movement first

        var sweptBox = createSweptBox(oldPos, newPos)

        var closestValidX = newPos.x
        var collidedHorizontally = false
        var collidedVertically = false

        for (box in Core.colliders) {
            if (box == collider) continue
            if (sweptBox.overlaps(box.rectangle)) {
                if (velocity.x > 0) {
                    // Moving right
                    closestValidX = box.getLeft() - collider.width
                    collidingDirection.right = true
                } else if (velocity.x < 0) {
                    // Moving left
                    closestValidX = box.getRight()
                    collidingDirection.left = true
                }

                collidedHorizontally = true

                break
            }
        }

        newPos = oldPos.cpy().add(0f, velocity.y)  // Now check vertical movement
        sweptBox = createSweptBox(oldPos, newPos)

        var closestValidY = newPos.y

        for (box in Core.colliders) {
            if (box == collider) continue
            if (sweptBox.overlaps(box.rectangle)) {
                if (velocity.y > 0) {
                    // Moving up
                    closestValidY = box.getBottom() - collider.height
                    collidingDirection.up = true
                } else if (velocity.y < 0) {
                    // Moving down
                    closestValidY = box.getTop()
                    collidingDirection.down = true
                }

                collidedVertically = true

                break
            }
        }

        if (collidedHorizontally && collidedVertically) {
            // Both collisions detected, handle corner case
            gameObject.transform.position.set(Round.round(closestValidX), Round.round(oldPos.y))
        } else {
            // No corner collision, proceed as before
            gameObject.transform.position.set(Round.round(closestValidX), Round.round(closestValidY))
        }
    }

    private fun createSweptBox(oldPos: Vector2, newPos: Vector2): Rectangle {
        // We create a larger box that starts at the old position and extends to the new position
        val x = min(oldPos.x, newPos.x)
        val y = min(oldPos.y, newPos.y)
        val width = abs(newPos.x - oldPos.x) + collider.width
        val height = abs(newPos.y - oldPos.y) + collider.height
        return Rectangle(x, y, width, height)
    }
}