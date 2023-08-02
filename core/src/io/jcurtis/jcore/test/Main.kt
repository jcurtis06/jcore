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
    val camera: GameObject = GameObject()
    private val player: GameObject = GameObject()
    private val map: GameObject = GameObject()

    override fun init() {
        showCollisionBoxes = false
        Core.assets.setLoader(TiledMap::class.java, TmxMapLoader())
        Core.assets.load("slime.png", Texture::class.java)
        Core.assets.load("player.png", Texture::class.java)
        Core.assets.load("badlogic.jpg", Texture::class.java)
        Core.assets.load("test.tmx", TiledMap::class.java)
        Core.assets.finishLoading()

        camera.attach<Camera>()
        camera.attach<SmoothedCamera>()

        map.attach<Tilemap>().apply {
            map = Core.assets.get("test.tmx", TiledMap::class.java)
        }
        map.attach<TilemapCollider>()

        player.attach<PlayerController>()
        player.attach<AnimationRenderer>().apply {
            addAnimation(
                "idle",
                AnimatedSpriteSheet(
                    Core.assets.get("player.png", Texture::class.java),
                    7,
                    1,
                    0.1f,
                    0,
                    0,
                    3,
                    1
                )
            )
        }
        player.attach<BoxCollider>().apply {
            width = 14f
            height = 12f
        }
        player.attach<RigidBody>()
        player.getComponent<AnimationRenderer>()?.play("idle")
        player.getComponent<AnimationRenderer>()?.flipH = true
    }
}