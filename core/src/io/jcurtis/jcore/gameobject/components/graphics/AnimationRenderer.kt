package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.graphics.Renderable
import io.jcurtis.platformer.graphics.AnimatedSpriteSheet
import kotlin.math.roundToInt

/**
 * A component that renders an animation.
 * @property flipH Whether to flip the animation horizontally.
 * @property offset The offset of the animation from the game object's position.
 */
@Suppress("MemberVisibilityCanBePrivate")
class AnimationRenderer : Component(), Renderable {
    var flipH = false
    var offset = Vector2()

    private var spriteSheetMap = mutableMapOf<String, AnimatedSpriteSheet>()
    private var animation: Animation<TextureRegion>? = null

    private var currentAnimation: AnimatedSpriteSheet? = null
        set(value) {
            animation = Animation(value!!.duration, *value.splitAnimation())
            field = value
        }


    private var stateTime = 0f

    /**
     * Adds an animation to the component.
     * @param name The name of the animation.
     * @param sheet The sprite sheet to use for the animation.
     */
    fun addAnimation(name: String, sheet: AnimatedSpriteSheet) {
        spriteSheetMap[name] = sheet
    }

    /**
     * Plays an animation if it exists.
     * @param animation The name of the animation to play.
     * @see addAnimation
     */
    fun play(animation: String) {
        if (spriteSheetMap[animation] == null) throw Exception("Animation $animation does not exist.")
        if (currentAnimation == spriteSheetMap[animation]) return
        currentAnimation = spriteSheetMap[animation]
    }

    /**
     * @return The current frame of the animation.
     */
    private fun getCurrentFrame(): TextureRegion {
        val currentFrame = animation!!.getKeyFrame(stateTime, true)

        if (flipH && !currentFrame.isFlipX) {
            currentFrame.flip(true, false)
        } else if (!flipH && currentFrame.isFlipX) {
            currentFrame.flip(true, false)
        }

        return currentFrame
    }

    override fun update(delta: Float) {
        stateTime += delta
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(
            getCurrentFrame(),
            (transform.position.x + offset.x).roundToInt().toFloat(),
            (transform.position.y + offset.y).roundToInt().toFloat()
        )
    }

    override fun setView(camera: OrthographicCamera) = Unit
    override fun init() = Unit
}