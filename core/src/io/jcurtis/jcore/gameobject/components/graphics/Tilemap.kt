package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.graphics.Renderable

class Tilemap : Component(), Renderable {
    var map: TiledMap = TiledMap()
        set(value) {
            field = value
            renderer = OrthogonalTiledMapRenderer(value)
            renderer.setView(Core.currentCamera)
        }

    private var renderer: OrthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(map)

    fun getObject(layer: String, name: String): MapObject? {
        val objectLayer = map.layers[layer] ?: return null
        return objectLayer.objects[name] ?: return null
    }

    fun getObjects(layer: String): List<MapObject> {
        val objectLayer = map.layers[layer] ?: return emptyList()
        val objects = mutableListOf<MapObject>()
        for (obj in objectLayer.objects) {
            objects.add(obj)
        }
        return objects
    }

    override fun update(delta: Float) {
        return
    }

    override fun init() {
        return
    }

    override fun render(batch: SpriteBatch) {
        renderer.render()
    }

    override fun setView(camera: OrthographicCamera) {
        renderer.setView(camera)
    }
}