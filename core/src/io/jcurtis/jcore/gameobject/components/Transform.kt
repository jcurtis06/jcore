package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Vector2
import kotlin.math.roundToInt

class Transform : Component() {
    var position = Vector2()

    var x = position.x
        set(value) {
            position.x = value
            field = value
        }

    var y = position.y
        set(value) {
            position.y = value
            field = value
        }

    fun getPositionRound(): Vector2 {
        return position.set(position.x.roundToInt().toFloat(), position.y.roundToInt().toFloat())
    }

    override fun update(delta: Float) {
        return
    }

    override fun init() {
        return
    }
}