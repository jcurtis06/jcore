package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
import io.jcurtis.jcore.gameobject.GameObject

/**
 * Adds collisions to a [Tilemap] component
 */
class TilemapCollider: Component() {
    private lateinit var tilemap: Tilemap

    override fun init() {
        try {
            tilemap = gameObject.getComponent()!!
        } catch (e: NullPointerException) {
            throw Exception("TilemapCollider requires a Tilemap component")
        }
    }

    override fun postInit() {
        // Try to retrieve the collision layer from the tilemap, if it doesn't exist, throw an exception
        val collisionLayer = tilemap.map.layers.get("collision") as TiledMapTileLayer?
                ?: throw Exception("TilemapCollider requires a collision layer")

        // Create a 2D array (grid) to keep track of the tiles that have been explored
        val exploredTiles = Array(collisionLayer.width) { BooleanArray(collisionLayer.height) }

        // Create a list to store the rectangles (colliders) created from the collision layer
        val colliderList = mutableListOf<Rectangle>()

        // Traverse all the cells in the collision layer
        for (tileY in 0 until collisionLayer.height) {
            for (tileX in 0 until collisionLayer.width) {
                // If the current cell has not been explored
                if (!isExplored(tileX, tileY, exploredTiles, collisionLayer)) {
                    // Explore the cell and return a Rectangle if a contiguous collider is found
                    val collider = explore(tileX, tileY, exploredTiles, collisionLayer)
                    // If a collider is found, add it to the colliders list
                    if (collider != null)
                        colliderList.add(collider)
                }
            }
        }

        // For each collider in the colliders list
        colliderList.forEach{ collider ->
            // Create a new game object
            val gameObject = GameObject()
            // Set its x and y position according to the collider
            gameObject.transform!!.position.x = collider.x * 16f
            gameObject.transform!!.position.y = collider.y * 16f
            // Attach a BoxCollider to the game object and set its width and height
            gameObject.attach<BoxCollider>().apply {
                width = collider.width * 16f
                height = collider.height * 16f
            }
        }
    }

    // Marks all tiles in the current row as explored
    private fun markRowAsExplored(startX: Int, y: Int, width: Int, explored: Array<BooleanArray>) {
        for (i in 0 until width)
            explored[startX + i][y] = true
    }

    // Explore tiles starting from a given position (x, y)
    private fun explore(startX: Int, startY: Int, explored: Array<BooleanArray>, tl: TiledMapTileLayer): Rectangle? {
        var currentX = startX
        var currentY = startY
        var currentWidth = 0
        var currentHeight = 0
        var definedWidth = 0
        var isWidthDefined = false

        while (true) {
            if (isExplored(currentX, currentY, explored, tl))
                break

            currentX++
            currentWidth++

            if ((isWidthDefined && currentWidth >= definedWidth) || isExplored(currentX, currentY, explored, tl)) {
                if (isWidthDefined && currentWidth < definedWidth)
                    break
                if (!isWidthDefined) {
                    isWidthDefined = true
                    definedWidth = currentWidth
                }
                markRowAsExplored(startX, currentY, definedWidth, explored)

                currentY++
                currentHeight++
                currentX = startX
                currentWidth = 0
            }
        }

        return if (definedWidth == 0 || currentHeight == 0)
            null
        else
            Rectangle(startX.toFloat(), startY.toFloat(), definedWidth.toFloat(), currentHeight.toFloat())
    }

    private fun isExplored(x: Int, y: Int, explored: Array<BooleanArray>, tl: TiledMapTileLayer): Boolean {
        return (
            x < 0 || y < 0 || // Check if x or y is negative, i.e., out of bounds of the tilemap
            x >= tl.width || y >= tl.height || // Check if x or y is beyond the tilemap's width or height
            explored[x][y] || // Check if the tile at coordinates x, y has already been explored
            tl.getCell(x, y) == null || // Check if there is no cell at the given coordinates
            !tl.getCell(x, y).tile.properties.get("collidable", Boolean::class.java) // Check if the cell at the given coordinates is not collidable
        )
    }


    override fun update(delta: Float) {
        return
    }
}