package io.jcurtis.jcore.platformer

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.JCoreGame
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.graphics.Tilemap
import io.jcurtis.jcore.gameobject.components.physics.TilemapCollider
import io.jcurtis.jcore.platformer.entities.Player

object Main : JCoreGame() {
    val camera = GameObject()
    val world = GameObject()

    lateinit var player: Player

    override fun init() {
        Core.assets.setLoader(TiledMap::class.java, TmxMapLoader())
        Core.assets.load("platformer/player.png", Texture::class.java)
        Core.assets.load("platformer/world/world.tmx", TiledMap::class.java)
        Core.assets.finishLoading()

        player = Player()

        world.attach<Tilemap>().apply {
            map = Core.assets.get("platformer/world/world.tmx", TiledMap::class.java)
            getObject("points", "player-spawn")?.let {
                val x = it.properties["x"] as Float
                val y = it.properties["y"] as Float
                player.obj.transform.position.set(x, y)
            }
        }

        world.attach<TilemapCollider>().apply {
            collisionTag = "collision"
        }
    }
}