package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import io.jcurtis.jcore.gameobject.GameObject

class TilemapCollider: Component() {
    private var tilemap: Tilemap? = null

    override fun init() {
        return
    }

    override fun postInit() {
        println("tilemap collider post init")
        tilemap = gameObject.getComponent<Tilemap>()

        if (tilemap == null) {
            throw Exception("TilemapCollider requires a Tilemap component")
        }

        val collisionLayer = tilemap!!.map.layers.get("collision") as TiledMapTileLayer?
            ?: throw Exception("TilemapCollider requires a collision layer")

        for (x in 0 until collisionLayer.width) {
            for (y in 0 until collisionLayer.height) {
                val cell = collisionLayer.getCell(x, y)

                if (cell != null) {
                    val collidable = cell.tile.properties.get("collidable", Boolean::class.java)

                    if (collidable == null || !collidable)
                        continue

                    val obj = GameObject()
                    obj.transform!!.position.x = x*16f
                    obj.transform!!.position.y = y*16f
                    obj.attach<BoxCollider>().apply {
                        width = 16f
                        height = 16f
                    }
                }
            }
        }
    }

    override fun update(delta: Float) {
        return
    }
}