package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.graphics.Renderable

/**
 * A component that renders a tilemap to the screen.
 * @property map The tilemap to render. Must be loaded with the asset manager.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class Tilemap : Component(), Renderable {
    var map: TiledMap = TiledMap()
        set(value) {
            field = value
            renderer = OrthogonalTiledMapRenderer(value)
            renderer.setView(Core.currentCamera)
        }

    private var renderer: OrthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(map)

    /**
     * Gets a specific object from a specific layer.
     * @param layer The layer to get the object from.
     * @param name The name of the object.
     * @return The object, or null if it doesn't exist.
     * @see MapObject
     * @see getObjects
     */
    fun getObject(layer: String, name: String): MapObject? {
        val objectLayer = map.layers[layer] ?: return null
        return objectLayer.objects[name]
    }

    /**
     * Gets all the objects from a specific layer.
     * @param layer The layer to get the objects from.
     * @return A list of all the objects in the layer.
     * @see MapObject
     */
    fun getObjects(layer: String): List<MapObject> {
        val objectLayer = map.layers[layer] ?: return emptyList()
        return objectLayer.objects.map { it }
    }

    override fun render(batch: SpriteBatch) {
        renderer.render()
    }

    override fun setView(camera: OrthographicCamera) {
        renderer.setView(camera)
    }

    override fun update(delta: Float) = Unit

    override fun init() = Unit
}