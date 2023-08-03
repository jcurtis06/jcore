package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.math.Vector2
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

    /**
     * Sets the center of the camera to the given position.
     * Must be called every frame to follow a changing position.
     */
    fun setCenter(pos: Vector2) {
        transform.position.set(pos)
        Core.currentCamera.position.set(pos.x, pos.y, 0f)
    }

    /**
     * Sets the center of the camera to the given position, but smoothly.
     * Must be called every frame to follow a changing position.
     * @param smoothing The amount of smoothing to use. Higher values result in slower smoothing.
     */
    fun setCenterSmooth(pos: Vector2, smoothing: Int = 10) {
        transform.position.set(pos)
        Core.currentCamera.position.x += (pos.x - Core.currentCamera.position.x) / smoothing
        Core.currentCamera.position.y += (pos.y - Core.currentCamera.position.y) / smoothing
    }

    override fun init() = Unit

    override fun update(delta: Float) = Unit
}