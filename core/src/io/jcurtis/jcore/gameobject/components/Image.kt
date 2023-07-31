package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject

class Image(gameObject: GameObject) : Component(gameObject) {
    var texture: Texture? = null

    init {
        Core.images.add(this)
    }

    fun draw(batch: SpriteBatch) {
        texture?.let {
            batch.draw(it, transform!!.position.x, transform!!.position.y)
        }
    }

    override fun update(delta: Float) {
        return
    }

    override fun init() {
        return
    }
}