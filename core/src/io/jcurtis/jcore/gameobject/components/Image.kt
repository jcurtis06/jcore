package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.jcurtis.jcore.core.Core
import kotlin.math.roundToInt

class Image : Component() {
    private var texture: Texture? = null
    private var sprite: Sprite = Sprite()

    var rotation = 0f
        set(value) {
            field = value
            sprite.rotation = value
        }

    init {
        Core.images.add(this)
    }

    fun setTexture(texture: Texture) {
        this.texture = texture
        sprite = Sprite(texture)
    }

    fun draw(batch: SpriteBatch) {
        if (texture == null) return
        sprite.setPosition(transform.position.x.roundToInt().toFloat(), transform.position.y.roundToInt().toFloat())
        sprite.draw(batch)
    }

    override fun update(delta: Float) {
        return
    }

    override fun init() {
        return
    }
}