package io.jcurtis.jcore.core

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import space.earlygrey.shapedrawer.ShapeDrawer
import kotlin.math.roundToInt

/**
 * The main class of the JCore library. Extend this class to create your game.
 * Handles the creation of the game window, the game loop, and the rendering of the game.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class JCoreGame : ApplicationAdapter() {
    var resWidth = 320
    var resHeight = 180

    var timeStep = 1 / 60f
    var velocityIterations = 6
    var positionIterations = 2

    private lateinit var batch: SpriteBatch
    private lateinit var pixelPerfectBuffer: FrameBuffer

    private lateinit var viewCamera: OrthographicCamera
    private var gameCamera = Core.currentCamera
    private lateinit var viewport: Viewport

    var showCollisionBoxes = false
    var showPositionPoints = false

    private lateinit var debugRenderer: ShapeDrawer

    private lateinit var box2dDebug: Box2DDebugRenderer

    private var firstFrame = true

    override fun create() {
        batch = SpriteBatch()

        viewCamera = OrthographicCamera()
        gameCamera.setToOrtho(false, resWidth.toFloat(), resHeight.toFloat())

        viewport = FitViewport(resWidth.toFloat(), resHeight.toFloat(), viewCamera)
        viewport.apply()

        pixelPerfectBuffer = FrameBuffer(Pixmap.Format.RGB888, resWidth, resHeight, false)
        pixelPerfectBuffer.colorBufferTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)

        debugRenderer = ShapeDrawer(batch, TextureRegion(Texture(Gdx.files.internal("pixel.png"))))
        box2dDebug = Box2DDebugRenderer()

        init()

        if (Core.objectsToAdd.isNotEmpty()) {
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
            Core.objects.addAll(Core.objectsToAdd)
            Core.objectsToAdd.forEach { it.init() }
            Core.objectsToAdd.clear()
        }

        Core.objects.forEach {
            it.update(Gdx.graphics.deltaTime)
        }

        Core.world.step(timeStep, velocityIterations, positionIterations);

        gameCamera.position.set(gameCamera.position.x.roundToInt().toFloat(), gameCamera.position.y.roundToInt().toFloat(), 0f)
        gameCamera.update()
        Core.renderables.forEach { it.setView(gameCamera) }

        pixelPerfectBuffer.begin()

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = gameCamera.combined
        batch.begin()

        Core.images.forEach { it.draw(batch) }
        Core.renderables.forEach { it.render(batch) }

        batch.end()
        pixelPerfectBuffer.end()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        viewport.apply()
        batch.projectionMatrix = viewCamera.combined
        viewCamera.setToOrtho(false, resWidth.toFloat(), resHeight.toFloat())

        batch.begin()
        batch.draw(
            pixelPerfectBuffer.colorBufferTexture, 0f, 0f, viewport.worldWidth, viewport.worldHeight, 0f, 0f, 1f, 1f
        )
        batch.end()

        box2dDebug.render(Core.world, gameCamera.combined)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        viewCamera.update()
    }
}