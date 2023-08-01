package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
import io.jcurtis.jcore.gameobject.GameObject

/*
This code is for a TilemapCollider class, which is a type of Component in a game engine. The purpose of this class is to handle the creation and management of colliders in a tile-based game map. Here's a high-level overview of what the code is doing:

Initialization: The TilemapCollider has an initialization function init() which currently does nothing.
Post-initialization: In the postInit() function, it fetches the Tilemap component from the game object and checks whether it exists. If it doesn't, it throws an exception.
Collision layer extraction: It retrieves a layer named "collision" from the Tilemap. If such a layer does not exist, it throws an exception. This collision layer represents areas in the game map where collision should be checked.
Exploration of the collision layer: It creates a two-dimensional boolean array, exploredTiles, to track which tiles have been explored. It then iterates over each tile in the collision layer. If a tile hasn't been explored, it calls the explore() function to potentially find a contiguous collider, which is represented as a Rectangle. Any found colliders are added to a list.
Creation of game objects for each collider: After all tiles have been explored, it iterates through each collider in the list. For each collider, it creates a GameObject and sets its position according to the collider. It then attaches a BoxCollider to the GameObject, setting its width and height according to the collider's dimensions.
Auxiliary functions: It has several helper functions, such as isExplored() to check if a tile has been explored or if it is a valid tile, markRowAsExplored() to mark all tiles in a row as explored, and explore() to explore tiles starting from a given position and potentially find a contiguous collider.
Update function: The update() function, which currently does nothing, would typically be used to update the component's state during each game loop.
In summary, this code seems to be part of a game engine handling tile-based maps. It's primarily concerned with managing collision detection by creating colliders for each contiguous group of collidable tiles in a tilemap.
 */


class TilemapCollider: Component() {
    private var tilemap: Tilemap? = null

    override fun init() {
        return
    }

    override fun postInit() {
        println("tilemap collider post init") // Print statement for debugging or logging purposes

        tilemap = gameObject.getComponent() // Fetch the Tilemap component from the game object

        // If the game object doesn't have a Tilemap component, throw an exception
        if (tilemap == null) {
            throw Exception("TilemapCollider requires a Tilemap component")
        }

        // Try to retrieve the collision layer from the tilemap, if it doesn't exist, throw an exception
        val collisionLayer = tilemap!!.map.layers.get("collision") as TiledMapTileLayer?
                ?: throw Exception("TilemapCollider requires a collision layer")

        // Create a 2D array (grid) to keep track of the tiles that have been explored
        val exploredTiles = Array(collisionLayer.width) { BooleanArray(collisionLayer.height) }

        // Create a list to store the rectangles (colliders) created from the collision layer
        val colliderList = mutableListOf<Rectangle>()

        // Traverse all the cells in the collision layer
        for (tileY in 0 until collisionLayer.height) {
            for (tileX in 0 until collisionLayer.width) {
                // If the current cell has not been explored
                if (!hasBeenExplored(tileX, tileY, exploredTiles, collisionLayer)) {
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

    // Checks if the tile at the given coordinates has been explored
    private fun isExplored(x: Int, y: Int, explored: Array<BooleanArray>, tl: TiledMapTileLayer): Boolean {
        return (x < 0 || y < 0 || x >= tl.width || y >= tl.height ||
                explored[x][y] ||
                tl.getCell(x, y) == null ||
                !tl.getCell(x, y).tile.properties.get("collidable", Boolean::class.java))
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

    private fun hasBeenExplored(x: Int, y: Int, explored: Array<BooleanArray>, tl: TiledMapTileLayer): Boolean {
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