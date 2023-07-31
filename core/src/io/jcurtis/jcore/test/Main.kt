package io.jcurtis.jcore.test

import com.badlogic.gdx.graphics.Texture
import io.jcurtis.jcore.core.Assets
import io.jcurtis.jcore.core.modules.RendererModule
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.BoxCollider
import io.jcurtis.jcore.gameobject.components.Image

class Main: RendererModule() {
    private val texture = Texture(Assets.load("slime.png"))

    private val player: GameObject = GameObject()

    override fun init() {
        player.attach<PlayerController>()
        player.attach<Image>().setTexture(texture)
        player.attach<BoxCollider>().apply {
            width = 16f
            height = 16f
        }
    }
}