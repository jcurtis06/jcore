package io.jcurtis.jcore.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Renderable {
    fun render(batch: SpriteBatch)

    fun setView(camera: OrthographicCamera)
}