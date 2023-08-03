package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import kotlin.math.roundToInt

/**
 * A component that renders a texture to the screen.
 * @property offset The offset of the image from the game object's position.
 * @property rotation The rotation of the image.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class Image : Component() {
    var offset = Vector2()

    var rotation = 0f
        set(value) {
            field = value
            sprite.rotation = value
        }

    private var sprite: Sprite = Sprite()

    init {
        Core.images.add(this)
    }

    /**
     * Sets the texture of the image.
     * @param texture The texture to use.
     */
    fun setTexture(texture: Texture) {
        sprite = Sprite(texture)
    }

    /**
     * Draws the image to the screen.
     * @param batch The sprite batch to draw with.
     */
    fun draw(batch: SpriteBatch) {
        sprite.setPosition(
            (transform.position.x + offset.x).roundToInt().toFloat(),
            (transform.position.y + offset.y).roundToInt().toFloat()
        )
        sprite.draw(batch)
    }

    override fun update(delta: Float) = Unit

    override fun init() = Unit
}