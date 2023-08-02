package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core
import kotlin.math.abs
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
        val minStep = 16.0f
        val new = transform.position.cpy().add(velocity)
        var steps = 1
        if(abs(velocity.x) > abs(velocity.y)) {
            if(abs(velocity.x) > minStep)
                steps = (abs(velocity.x) / minStep).roundToInt()
        }
        else {
            if(abs(velocity.y) > minStep)
                steps = (abs(velocity.x) / minStep).roundToInt()
        }

        var step = velocity.cpy().scl(1/steps.toFloat())

        for(i in 0 until steps) {
            transform.position.x += step.x

            for (box in Core.colliders) {
                if (box == collider) continue
                if (collider.rectangle.overlaps(box.rectangle)) {
                    if (step.x > 0) {
                        transform.position.x = box.getLeft() - collider.rectangle.width
                    } else {
                        transform.position.x = box.getRight()
                    }
                    velocity.x = 0f
                    return
                }
            }

            transform.position.y += step.y

            for (box in Core.colliders) {
                if (box == collider) continue
                if (collider.rectangle.overlaps(box.rectangle)) {
                    if (step.y > 0) {
                        transform.position.y = box.getTop() - collider.rectangle.height
                    } else {
                        transform.position.y = box.getBottom()
                    }
                    velocity.y = 0f
                    return
                }
            }
        }
        transform.position.set(new)
    }
}