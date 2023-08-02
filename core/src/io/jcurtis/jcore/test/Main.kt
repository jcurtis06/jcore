package io.jcurtis.jcore.test

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.JCoreGame
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.*
import io.jcurtis.platformer.graphics.AnimatedSpriteSheet

object Main: JCoreGame() {
    lateinit var player: Player
    private val map: GameObject = GameObject()

    override fun init() {
        showCollisionBoxes = false
        Core.assets.setLoader(TiledMap::class.java, TmxMapLoader())
        Core.assets.load("slime.png", Texture::class.java)
        Core.assets.load("player.png", Texture::class.java)
        Core.assets.load("badlogic.jpg", Texture::class.java)
        Core.assets.load("test.tmx", TiledMap::class.java)
        Core.assets.finishLoading()

        map.attach<Tilemap>().apply {
            map = Core.assets.get("test.tmx", TiledMap::class.java)
        }
        map.attach<TilemapCollider>()

        player = Player()
    }
}