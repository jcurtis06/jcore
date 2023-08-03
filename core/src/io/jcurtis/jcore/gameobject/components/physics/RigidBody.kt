package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.math.Round
import kotlin.math.abs
import kotlin.math.min

/**
 * A component that allows the game object to collide with other objects.
 * @property velocity The velocity of the game object to be applied in [moveAndSlide].
 * @property collidingDirection The direction that the game object is colliding in.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class RigidBody : Component() {
    var velocity = Vector2(0f, 0f)

    private lateinit var collider: BoxCollider

    var collidingDirection = CollidingDirection()

    override fun init() {
        try {
            collider = gameObject.getComponent<BoxCollider>()!!
        } catch (e: NullPointerException) {
            throw NullPointerException("RigidBody requires a BoxCollider component.")
        }
    }

    /**
     * Moves the game object based on [velocity] and handles collisions.
     * @see [velocity]
     * @see [CollidingDirection]
     */
    fun moveAndSlide() {
        val collidingDirectionNew = CollidingDirection()

        val oldPos = Vector2(transform.position)
        var newXPos = oldPos.x + velocity.x
        var newYPos = oldPos.y + velocity.y

        // Handle horizontal movement first
        val horizontalSweptBox = createSweptBox(oldPos, Vector2(newXPos, oldPos.y))

        val colliders = Core.colliders.filter { it != collider }
        for (box in colliders) {
            if (horizontalSweptBox.overlaps(box.rectangle)) {
                newXPos = if (velocity.x > 0) {
                    collidingDirectionNew.right = true
                    box.getLeft() - collider.width
                } else {
                    collidingDirectionNew.left = true
                    box.getRight()
                }
                break
            }
        }

        // Now handle vertical movement
        val verticalSweptBox = createSweptBox(oldPos, Vector2(oldPos.x, newYPos))
        for (box in colliders) {
            if (verticalSweptBox.overlaps(box.rectangle)) {
                newYPos = if (velocity.y > 0) {
                    collidingDirectionNew.up = true
                    box.getBottom() - collider.height
                } else {
                    collidingDirectionNew.down = true
                    box.getTop()
                }
                break
            }
        }

        gameObject.transform.position.set(Round.round(newXPos), Round.round(newYPos))

        // Update the colliding direction
        collidingDirection = collidingDirectionNew
    }

    /**
     * Creates a rectangle that spans from the old position to the new position with the colliders dimensions.
     */
    private fun createSweptBox(oldPos: Vector2, newPos: Vector2): Rectangle {
        val x = min(oldPos.x, newPos.x)
        val y = min(oldPos.y, newPos.y)
        val width = abs(newPos.x - oldPos.x) + collider.width
        val height = abs(newPos.y - oldPos.y) + collider.height
        return Rectangle(x, y, width, height)
    }

    override fun update(delta: Float) = Unit
}

data class CollidingDirection(
    var down: Boolean = false,
    var up: Boolean = false,
    var left: Boolean = false,
    var right: Boolean = false,
    var none: Boolean = true
)
