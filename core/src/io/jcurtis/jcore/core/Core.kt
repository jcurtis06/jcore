package io.jcurtis.jcore.core

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.BoxCollider
import io.jcurtis.jcore.gameobject.components.Image
import io.jcurtis.jcore.graphics.Renderable

object Core {
    var batch: SpriteBatch = SpriteBatch()

    var objects = mutableListOf<GameObject>()
    var colliders = mutableListOf<BoxCollider>()
    var images = mutableListOf<Image>()
    var renderables = mutableListOf<Renderable>()

    var objectsToAdd = mutableListOf<GameObject>()

    var camera: OrthographicCamera = OrthographicCamera()

    var assets = AssetManager()

    fun dispose() {
        batch.dispose()
    }
}