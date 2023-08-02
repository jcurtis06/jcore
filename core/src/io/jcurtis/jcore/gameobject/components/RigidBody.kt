package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.physics.Raycast
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

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

    fun moveAndSlide() {
        val oldPos = Vector2(gameObject.transform.position)
        val newPos = Vector2(gameObject.transform.position).add(velocity)

        val sweptBox = createSweptBox(oldPos, newPos)

        // The closest valid position for the object
        var closestValidX = newPos.x
        var closestValidY = newPos.y

        for (box in Core.colliders) {
            if (box == collider) continue
            if (sweptBox.overlaps(box.rectangle)) {
                println("found future collision")
                // We have a collision, so we need to find the closest valid position
                if (velocity.x > 0) {
                    // Moving right
                    println("collided right side")
                    closestValidX = box.getLeft() - collider.width
                    collidingDirection.right = true
                } else if (velocity.x < 0) {
                    // Moving left
                    println("collided left side")
                    closestValidX = box.getRight()
                    collidingDirection.left = true
                }

                if (velocity.y > 0) {
                    // Moving up
                    println("collided top side")
                    closestValidY = box.getBottom() - collider.height
                    collidingDirection.up = true
                } else if (velocity.y < 0) {
                    // Moving down
                    println("collided bottom side")
                    closestValidY = box.getTop()
                    collidingDirection.down = true
                }

                break
            }
        }

        // No collision, so move the object to a valid position
        gameObject.transform.position.set(closestValidX, closestValidY)
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