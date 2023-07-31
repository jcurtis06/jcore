package io.jcurtis.jcore.test

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.jcurtis.jcore.core.Assets
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.modules.RendererModule
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.Image
import io.jcurtis.jcore.graphics.surfaces.PixelPerfect

class Main: RendererModule() {
    private val texture = Texture(Assets.load("badlogic.jpg"))

    private val player: GameObject = GameObject()

    override fun preInit() {
        setResolution(320f, 240f)
    }

    override fun init() {
        player.attach<PlayerController>()
        player.attach<Image>().texture = texture
    }

    override fun draw(batch: SpriteBatch) {
    }
}