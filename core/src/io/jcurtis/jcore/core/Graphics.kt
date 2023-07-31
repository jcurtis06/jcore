package io.jcurtis.jcore.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import java.awt.Color

object Graphics {
    private var mouse = Vector2()
    private var size = Vector2()

    private var background = Color.BLACK


    fun clear(color: Color = background) {
        Gdx.gl.glClearColor(color.red*1f/255f, color.green*1f/255f, color.blue*1f/255f, color.alpha*1f/255f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    fun mouse(): Vector2 {
        return mouse.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
    }

    fun size(): Vector2 {
        return size.set(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    }

    fun width() = Gdx.graphics.width

    fun height() = Gdx.graphics.height

    fun delta() = Gdx.graphics.deltaTime
}