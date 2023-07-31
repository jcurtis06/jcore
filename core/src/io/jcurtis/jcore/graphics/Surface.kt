package io.jcurtis.jcore.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.utils.viewport.Viewport
import io.jcurtis.jcore.core.Graphics


/**
 * Essentially a wrapper for a [FrameBuffer] that allows for easier usage.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Surface() {
    var buffer: FrameBuffer? = null
    var linear = false
    var fixedSize = false
    var fixedWidth = 0
    var fixedHeight = 0

    init {
        onResize()
    }

    fun setSize(width: Int, height: Int, fixed: Boolean): Surface {
        fixedSize = fixed
        fixedWidth = width
        this.fixedHeight = height
        onResize()
        return this
    }

    fun width(): Int {
        return buffer!!.width
    }

    fun height(): Int {
        return buffer!!.height
    }

    fun draw(batch: Batch, viewport: Viewport) {
        batch.draw(texture(), 0f, 0f, viewport.worldWidth, viewport.worldHeight, 0f, 0f, 1f, 1f)
    }

    fun onResize() {
        if (!fixedSize) {
            if (buffer != null) {
                buffer!!.dispose()
            }

            buffer = FrameBuffer(
                Pixmap.Format.RGB888,
                Graphics.width(),
                Graphics.height(),
                false
            )
        } else if (buffer == null || buffer!!.width != fixedWidth || buffer!!.height != fixedHeight) {
            if (buffer != null) {
                buffer!!.dispose()
            }
            buffer = FrameBuffer(Pixmap.Format.RGBA8888, fixedWidth, fixedHeight, false)
        }
        if (!linear) buffer!!.colorBufferTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest)
    }

    fun texture(): Texture? {
        return buffer!!.colorBufferTexture
    }

    fun begin() {
        begin(true)
    }

    fun begin(clear: Boolean) {
        begin(clear, true)
    }

    fun begin(clear: Boolean, viewport: Boolean) {
        if (viewport) {
            buffer!!.begin()
        } else {
            buffer!!.bind()
        }
        if (clear) Graphics.clear()
    }

    fun end() {
        buffer!!.end()
    }

    fun dispose() {
        buffer!!.dispose()
    }
}