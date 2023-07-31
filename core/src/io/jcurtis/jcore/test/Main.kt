package io.jcurtis.jcore.test

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.modules.RendererModule
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.*

class Main: RendererModule() {
    private val player: GameObject = GameObject()
    private val map: GameObject = GameObject()

    override fun init() {
        Core.assets.setLoader(TiledMap::class.java, TmxMapLoader())
        Core.assets.load("slime.png", Texture::class.java)
        Core.assets.load("badlogic.jpg", Texture::class.java)
        Core.assets.load("test.tmx", TiledMap::class.java)
        Core.assets.finishLoading()

        map.attach<Tilemap>().apply {
            map = Core.assets.get("test.tmx", TiledMap::class.java)
        }
        map.attach<TilemapCollider>()

        player.attach<PlayerController>()
        player.attach<Image>().setTexture(Core.assets.get("slime.png", Texture::class.java))
        player.attach<BoxCollider>().apply {
            width = 16f
            height = 16f
        }
        player.attach<Rigidbody>()
    }
}