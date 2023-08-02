package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Vector2
import kotlin.math.roundToInt

/**
 * Smoothly follows a target position
 */
class SmoothedCamera: Component() {
    private var targetX = 0
    private var targetY = 0
    var smoothing = 10

    fun setTarget(x: Int, y: Int) {
        targetX = x
        targetY = y
    }

    fun setTarget(vec: Vector2) {
        targetX = vec.x.toInt()
        targetY = vec.y.toInt()
    }

    override fun update(delta: Float) {
        transform.position.x += (targetX - transform.position.x) / smoothing
        transform.position.y += (targetY - transform.position.y) / smoothing
    }
    override fun init() {
        return
    }
}