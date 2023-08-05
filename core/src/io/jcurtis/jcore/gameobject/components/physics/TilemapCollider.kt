package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.objects.PolygonMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.graphics.Tilemap

/**
 * Adds collisions to a [Tilemap] component
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class TilemapCollider(
    override var onEnter: (GameObject) -> Unit = {},
    override var onExit: (GameObject) -> Unit = {},
    override var onStay: (GameObject) -> Unit = {}
) : Component(), CollisionListener {
    private lateinit var tilemap: Tilemap

    var tileLayer = 0
    var collisionTag = "collision"
    var tileSize = 16f

    override fun init() {
        try {
            tilemap = gameObject.getComponent()!!
        } catch (e: NullPointerException) {
            throw Exception("TilemapCollider requires a Tilemap component")
        }
    }

    override fun postInit() {
        buildShapes()
    }

    private fun buildShapes() {
        // Try to retrieve the collision layer from the tilemap, if it doesn't exist, throw an exception
        val collisionLayer = tilemap.map.layers[collisionTag]
            ?: throw Exception("TilemapCollider requires a collision layer")

        val layer = collisionLayer as TiledMapTileLayer

        // Loop through each cell in the collision layer
        for (x in 0 until layer.width) {
            for (y in 0 until layer.height) {
                val cell = layer.getCell(x, y) ?: continue
                if (cell.tile == null) continue

                // Loop through the embedded objects in the cell
                for (obj in cell.tile.objects) {
                    val pos = Vector2(x*tileSize, y*tileSize)

                    val shape: Shape = if (obj is RectangleMapObject) {
                        getRectangle(obj, pos)
                    } else if (obj is PolygonMapObject) {
                        getPolygon(obj, pos)
                    } else {
                        continue
                    }

                    // Create a body for the shape
                    val bd = BodyDef()
                    bd.type = BodyDef.BodyType.StaticBody
                    val body = Core.world.createBody(bd)
                    body.createFixture(shape, 1.0f)
                    body.userData = gameObject
                    shape.dispose()
                }
            }
        }
    }

    private fun getRectangle(rectangleObject: RectangleMapObject, pos: Vector2): PolygonShape {
        val rectangle = rectangleObject.rectangle
        val polygon = PolygonShape()
        val size = Vector2(
            (pos.x + rectangle.width * 0.5f),
            (pos.y + rectangle.height * 0.5f)
        )

        polygon.setAsBox(
            rectangle.width * 0.5f,
            rectangle.height * 0.5f,
            size,
            0.0f
        )

        return polygon
    }

    private fun getPolygon(polygonObject: PolygonMapObject, pos: Vector2): PolygonShape {
        val polygon = PolygonShape()
        val vertices = polygonObject.polygon.transformedVertices
        val worldVertices = FloatArray(vertices.size)

        for (i in vertices.indices) {
            worldVertices[i] = vertices[i] + pos.x
        }

        polygon.set(worldVertices)
        return polygon
    }

    override fun update(delta: Float) = Unit
}