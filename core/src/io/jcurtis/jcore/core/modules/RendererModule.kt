package io.jcurtis.jcore.core.modules

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.Graphics
import io.jcurtis.jcore.graphics.Surface
import io.jcurtis.jcore.graphics.surfaces.PixelPerfect

abstract class RendererModule: Module() {
    var surfaces = mutableListOf<Surface>()

    var resolution = Vector2(Graphics.size().x, Graphics.size().y)

    private var surfaceCamera = OrthographicCamera()
    private var viewport = FitViewport(Graphics.size().x, Graphics.size().y, surfaceCamera)
    private var pixelPerfect = PixelPerfect(resolution.x.toInt(), resolution.y.toInt())

    private fun drawDefault() {
        Core.camera.update()

        pixelPerfect.begin()

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        Core.batch.projectionMatrix = Core.camera.combined

        Core.batch.begin()

        draw()

        Core.batch.end()
        pixelPerfect.end()

        Graphics.clear()

        viewport.apply()
        surfaceCamera.setToOrtho(false, resolution.x, resolution.y)
        Core.batch.projectionMatrix = surfaceCamera.combined
        Core.batch.begin()
        pixelPerfect.draw(Core.batch, viewport)
        for (surface in surfaces) {
            surface.draw(Core.batch, viewport)
        }
        Core.batch.end()
    }

    abstract fun draw(batch: SpriteBatch = Core.batch)

    override fun update() {
        drawDefault()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        surfaceCamera.update()
    }

    fun setResolution(width: Float, height: Float) {
        resolution.set(width, height)
        pixelPerfect.dispose()
        pixelPerfect = PixelPerfect(resolution.x.toInt(), resolution.y.toInt())
    }
}