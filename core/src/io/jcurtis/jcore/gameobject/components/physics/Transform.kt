package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.gameobject.components.Component
import kotlin.math.roundToInt

class Transform : Component() {
    var position = Vector2()

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