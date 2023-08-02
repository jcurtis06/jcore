package io.jcurtis.jcore.gameobject.components

import io.jcurtis.jcore.core.Core

class Camera: Component() {
    var isActive = true

    override fun init() {
    }

    override fun update(delta: Float) {
        if (isActive) {
            Core.currentCamera.position.set(transform.position, 0f)
        }
    }
}