package io.jcurtis.jcore.math

import com.badlogic.gdx.math.Vector2
import kotlin.math.roundToInt

object Round {
    fun round(num: Float): Float {
        return num.roundToInt().toFloat()
    }

    fun round(vec: Vector2): Vector2 {
        return Vector2(round(vec.x), round(vec.y))
    }
}