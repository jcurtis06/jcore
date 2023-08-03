package io.jcurtis.jcore.test

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.physics.box2d.PolygonShape
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.JCoreGame
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.graphics.AnimationRenderer
import io.jcurtis.jcore.gameobject.components.graphics.Camera
import io.jcurtis.jcore.gameobject.components.graphics.Tilemap
import io.jcurtis.jcore.gameobject.components.physics.DynamicBody
import io.jcurtis.jcore.gameobject.components.physics.StaticBody
import io.jcurtis.jcore.gameobject.components.physics.TilemapCollider
import io.jcurtis.platformer.graphics.AnimatedSpriteSheet

object Main : JCoreGame() {
    private val player: GameObject = GameObject()
    private val map: GameObject = GameObject()
    val camera: GameObject = GameObject()

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
            getObject("objects", "player-spawn")?.let {
                val x = it.properties["x"] as Float
                val y = it.properties["y"] as Float
                player.transform.position.set(x, y)
            }
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
        player.attach<DynamicBody>().apply {
            gravityScale = 0f
            collider = PolygonShape().apply {
                setAsBox(8f, 8f)
            }
        }
        player.getComponent<AnimationRenderer>()?.play("idle")
        player.getComponent<AnimationRenderer>()?.flipH = true

        val ground = GameObject()
        ground.attach<StaticBody>().apply {
            collider = PolygonShape().apply {
                setAsBox(100f, 1f)
            }
        }

        camera.attach<Camera>()
    }
}