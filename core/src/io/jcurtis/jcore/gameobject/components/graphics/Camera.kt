package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component

/**
 * A component that controls the main camera.
 * @property isActive Whether the camera is active.
 * @see Core.currentCamera The current camera that the game is rendering with.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class Camera : Component() {
    var isActive = true
    var smoothing = 0.1f
    var target: Vector2 = Vector2()

    override fun init() = Unit

    override fun update(delta: Float) {
        if (isActive) {
            val newPos = Core.currentCamera.position.lerp(Vector3(target, 0f), smoothing)

            Core.currentCamera.position.set(newPos)
        }
    }
}