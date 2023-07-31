package io.jcurtis.jcore.test

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.jcurtis.jcore.core.Assets
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.modules.RendererModule
import io.jcurtis.jcore.graphics.surfaces.PixelPerfect

class Main: RendererModule() {
    private val texture = Texture(Assets.load("badlogic.jpg"))
    private val sprite = Sprite(texture)

    init {
        setResolution(320f, 240f)
    }

    override fun draw(batch: SpriteBatch) {
        sprite.rotate(1f)
        sprite.draw(batch)
    }
}