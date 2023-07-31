package io.jcurtis.jcore.core

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.BoxCollider
import io.jcurtis.jcore.gameobject.components.Image

object Core {
    var batch: SpriteBatch = SpriteBatch()

    var objects = mutableListOf<GameObject>()
    var colliders = mutableListOf<BoxCollider>()
    var images = mutableListOf<Image>()

    fun dispose() {
        batch.dispose()
    }
}