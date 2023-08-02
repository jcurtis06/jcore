package io.jcurtis.jcore.gameobject.components.graphics

import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component

class Camera : Component() {
    var isActive = true

    override fun init() {
    }

    override fun update(delta: Float) {
        if (isActive) {
            Core.currentCamera.position.set(transform.position, 0f)
        }
    }
}