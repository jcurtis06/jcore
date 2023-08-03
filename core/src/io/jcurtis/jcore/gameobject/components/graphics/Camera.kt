package io.jcurtis.jcore.gameobject.components.graphics

import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import kotlin.math.roundToInt

class Camera : Component() {
    var isActive = true

    override fun init() {
    }

    override fun update(delta: Float) {
        if (isActive) {
            Core.currentCamera.position.set(transform.position.x.roundToInt().toFloat(), transform.position.y.roundToInt().toFloat(), 0f)
        }
    }
}