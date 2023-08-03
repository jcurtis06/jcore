package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import kotlin.math.roundToInt

class Camera : Component() {
    var isActive = true

    var smoothingEnabled = false
    var smoothing = 10

    fun setCenter(pos: Vector2) {
        transform.position.set(pos)
        Core.currentCamera.position.set(pos.x, pos.y, 0f)
    }

    fun setCenterSmooth(pos: Vector2) {
        transform.position.set(pos)
        Core.currentCamera.position.x += (pos.x - Core.currentCamera.position.x) / 10
        Core.currentCamera.position.y += (pos.y - Core.currentCamera.position.y) / 10
    }

    override fun init() {
    }

    override fun update(delta: Float) {
    }
}