package io.jcurtis.jcore.core

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import space.earlygrey.shapedrawer.ShapeDrawer
import kotlin.math.roundToInt

open class JCoreGame: ApplicationAdapter() {
    val resWidth = 320
    val resHeight = 180

    var batch: SpriteBatch? = null
    var pixelPerfectBuffer: FrameBuffer? = null
    var viewCamera: OrthographicCamera? = null
    var gameCamera: OrthographicCamera? = null
    var viewport: Viewport? = null

    var showCollisionBoxes = false

    var debugRenderer: ShapeDrawer? = null

    private var firstFrame = true

    override fun create() {
        batch = SpriteBatch()

        viewCamera = OrthographicCamera()
        gameCamera = OrthographicCamera()
        gameCamera!!.setToOrtho(false, resWidth.toFloat(), resHeight.toFloat())

        viewport = FitViewport(resWidth.toFloat(), resHeight.toFloat(), viewCamera)
        viewport!!.apply()

        pixelPerfectBuffer = FrameBuffer(Pixmap.Format.RGB888, resWidth, resHeight, false)
        pixelPerfectBuffer!!.colorBufferTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)

        debugRenderer = ShapeDrawer(batch, TextureRegion(Texture(Gdx.files.internal("pixel.png"))))

        init()

        if (Core.objectsToAdd.isNotEmpty()) {
            println("adding ${Core.objectsToAdd.size} objects")
            Core.objects.addAll(Core.objectsToAdd)
            Core.objectsToAdd.clear()
            Core.objects.forEach { it.init() }
        }
    }

    open fun init() {}

    open fun postInit() {}

    override fun render() {
        if (firstFrame) {
            postInit()
            Core.objects.forEach { it.postInit() }

            firstFrame = false
        }

        if (Core.objectsToAdd.isNotEmpty()) {
            println("adding ${Core.objectsToAdd.size} objects")
            Core.objects.addAll(Core.objectsToAdd)
            Core.objectsToAdd.clear()
            Core.objects.forEach { it.init() }
        }

        Core.objects.forEach {
            it.transform.position.set(
                it.transform.position.x.roundToInt().toFloat(),
                it.transform.position.y.roundToInt().toFloat()
            )
            it.update(Gdx.graphics.deltaTime)
        }

        gameCamera!!.update()
        Core.renderables.forEach { it.setView(gameCamera!!) }

        pixelPerfectBuffer!!.begin()

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch!!.projectionMatrix = gameCamera!!.combined
        batch!!.begin()

        Core.images.forEach { it.draw(batch!!) }
        Core.renderables.forEach { it.render(batch!!) }
        Core.objects.forEach {
            debugRenderer!!.setColor(0f, 1f, 0f, 1f)

            debugRenderer!!.rectangle(
                it.transform.position.x,
                it.transform.position.y,
                1f,
                1f
            )
        }

        if (showCollisionBoxes) {
            Core.colliders.forEach {
                debugRenderer!!.setColor(0f, 0f, 1f, 0.2f)
                debugRenderer!!.rectangle(
                    it.rectangle
                )

                debugRenderer!!.setColor(1f, 0f, 0f, 0.5f)
                debugRenderer!!.filledRectangle(
                    it.rectangle
                )
            }
        }

        batch!!.end()
        pixelPerfectBuffer!!.end()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        viewport!!.apply()
        batch!!.projectionMatrix = viewCamera!!.combined
        viewCamera!!.setToOrtho(false, resWidth.toFloat(), resHeight.toFloat())

        batch!!.begin()
        batch!!.draw(
            pixelPerfectBuffer!!.colorBufferTexture,
            0f, 0f,
            viewport!!.worldWidth, viewport!!.worldHeight,
            0f, 0f,
            1f, 1f
        )
        batch!!.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport!!.update(width, height)
        viewCamera!!.update()
    }
}