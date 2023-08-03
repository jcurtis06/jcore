package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.gameobject.components.Component
import kotlin.math.roundToInt

/**
 * A component that adds position to a game object.
 * All game objects have a transform component by default.
 * @property position The position of the game object.
 */
class Transform : Component() {
    var position = Vector2()

    override fun update(delta: Float) = Unit
    override fun init() = Unit
}