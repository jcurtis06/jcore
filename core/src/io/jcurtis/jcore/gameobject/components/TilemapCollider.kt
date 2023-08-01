package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
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

        val explored = Array(collisionLayer.width) { BooleanArray(collisionLayer.height) }
        val colliders = mutableListOf<Rectangle>()
        for (y in 0 until collisionLayer.height) {
            for (x in 0 until collisionLayer.width) {
                if (!hasBeenExplored(x, y, explored, collisionLayer)) {
                    val col = explore(x, y, explored, collisionLayer)
                    if (col != null)
                        colliders.add(col)
                }
            }
        }
        colliders.forEach{
            val obj = GameObject()
            obj.transform!!.position.x = it.x * 16f
            obj.transform!!.position.y = it.y * 16f
            obj.attach<BoxCollider>().apply {
                width = it.width * 16f
                height = it.height * 16f
            }
        }
        /*
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
        }*/
    }

    fun explore(x: Int, y: Int, explored: Array<BooleanArray>, tl: TiledMapTileLayer): Rectangle? {
        var cx = x
        var cy = y
        var cw = 0
        var ch = 0
        var defW = 0 // defined width
        var hasDef = false // is width defined yet
        val lastRow = mutableListOf<Vector2>()
        while (true) {
            if (hasBeenExplored(cx, cy, explored, tl))
                break

            cx++
            cw++
            if ((hasDef && cw >= defW) ||
                hasBeenExplored(cx, cy, explored, tl)) { // Row is complete
                if (hasDef && cw < defW)
                    break
                if (!hasDef) {
                    hasDef = true
                    defW = cw
                }
                for (i in 0 until defW)
                    explored[x + i][cy] = true
                lastRow.clear()
                cy++
                ch++
                cx = x
                cw = 0
            }
        }
        if (defW == 0 || ch == 0)
            return null
        return Rectangle(x.toFloat(), y.toFloat(), defW.toFloat(), ch.toFloat())
    }

    fun hasBeenExplored(x: Int, y: Int, explored: Array<BooleanArray>, tl: TiledMapTileLayer): Boolean {
        return (x < 0 || y < 0 || x >= tl.width || y >= tl.height ||
                explored[x][y] ||
                tl.getCell(x, y) == null ||
                !tl.getCell(x, y).tile.properties.get("collidable", Boolean::class.java))
    }

    override fun update(delta: Float) {
        return
    }
}