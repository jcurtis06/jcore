package io.jcurtis.jcore.gameobject.components.graphics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.graphics.Renderable
import io.jcurtis.platformer.graphics.AnimatedSpriteSheet

class AnimationRenderer : Component(), Renderable {
    private var spriteSheetMap = mutableMapOf<String, AnimatedSpriteSheet>()
    private var animation: Animation<TextureRegion>? = null

    var flipH = false

    private var currentAnimation: AnimatedSpriteSheet? = null
        set(value) {
            animation = Animation(value!!.duration, *value.splitAnimation())
            field = value
        }


    private var stateTime = 0f

    fun play(animation: String) {
        currentAnimation = spriteSheetMap[animation]
    }

    fun addAnimation(name: String, sheet: AnimatedSpriteSheet) {
        spriteSheetMap[name] = sheet
    }

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
        batch.draw(getCurrentFrame(), transform.position.x, transform.position.y)
    }

    override fun setView(camera: OrthographicCamera) {
    }

    override fun init() {
    }
}