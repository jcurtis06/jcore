package io.jcurtis.jcore.core.modules

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import io.jcurtis.jcore.core.Core

abstract class RendererModule: Module() {
    private val VIRTUAL_WIDTH = 320
    private val VIRTUAL_HEIGHT = 180

    var batch: SpriteBatch? = null

    var frameBuffer: FrameBuffer? = null

    var fboCamera: OrthographicCamera? = null

    var camera: OrthographicCamera? = null

    var viewport: Viewport? = null

    override fun preInit() {
        batch = SpriteBatch()

        // Create both cameras
        fboCamera = OrthographicCamera()
        camera = OrthographicCamera()
        camera!!.setToOrtho(false, VIRTUAL_WIDTH.toFloat(), VIRTUAL_HEIGHT.toFloat())

        // Create the viewport
        viewport = FitViewport(VIRTUAL_WIDTH.toFloat(), VIRTUAL_HEIGHT.toFloat(), fboCamera)
        viewport!!.apply()

        // Create a new FrameBuffer with a specific resolution
        frameBuffer = FrameBuffer(Pixmap.Format.RGB888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false)
        frameBuffer!!.colorBufferTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
    }

    fun drawDefault() {
        Core.objects.forEach { it.update(Gdx.graphics.deltaTime) }

        camera!!.update()

        // Start rendering to the FrameBuffer
        frameBuffer!!.begin()

        // Clear the screen with a white color
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Set the projection matrix to the camera's combined matrix
        batch!!.projectionMatrix = camera!!.combined

        // Begin the batch
        batch!!.begin()
        // Everything in here will be rendered to the FrameBuffer
        // Thus making it pixel-perfect

        Core.images.forEach { it.draw(batch!!) }


        // End the batch
        batch!!.end()

        // Stop rendering to the FrameBuffer
        frameBuffer!!.end()

        // Clear the screen with a black color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        viewport!!.apply()
        batch!!.projectionMatrix = fboCamera!!.combined
        fboCamera!!.setToOrtho(false, VIRTUAL_WIDTH.toFloat(), VIRTUAL_HEIGHT.toFloat())

        // Render the FrameBuffer to the screen, scaling it to the screen's resolution
        batch!!.begin()
        batch!!.draw(
            frameBuffer!!.colorBufferTexture,
            0f,
            0f,
            viewport!!.worldWidth,
            viewport!!.worldHeight,
            0f,
            0f,
            1f,
            1f
        )
        batch!!.end()
    }

    open fun draw() {}

    override fun update() {
        drawDefault()
        draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport!!.update(width, height)
        fboCamera!!.update()
    }

    override fun dispose() {
        batch!!.dispose()
        frameBuffer!!.dispose()
    }
}