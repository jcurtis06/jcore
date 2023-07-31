package io.jcurtis.jcore.core

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.Image

object Core {
    var camera = OrthographicCamera()
    var batch: SpriteBatch = SpriteBatch()

    var objects = mutableListOf<GameObject>()
    var images = mutableListOf<Image>()

    init {
        camera.setToOrtho(false, Graphics.size().x, Graphics.size().y)
    }

    fun dispose() {
        batch.dispose()
    }
}