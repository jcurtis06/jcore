package io.jcurtis.jcore.core

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch

object Core {
    var camera = OrthographicCamera()
    var batch: SpriteBatch = SpriteBatch()

    init {
        camera.setToOrtho(false, Graphics.size().x, Graphics.size().y)
    }

    fun dispose() {
        batch.dispose()
    }
}