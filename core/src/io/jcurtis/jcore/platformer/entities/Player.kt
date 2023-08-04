package io.jcurtis.jcore.platformer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.graphics.AnimationRenderer
import io.jcurtis.jcore.gameobject.components.physics.BoxShape
import io.jcurtis.jcore.gameobject.components.physics.DynamicBody
import io.jcurtis.platformer.graphics.AnimatedSpriteSheet

class Player {
    val obj = GameObject()

    init {
        obj.attach<AnimationRenderer>().apply {
            addAnimation(
                "idle",
                AnimatedSpriteSheet(
                    Core.assets.get("platformer/player.png", Texture::class.java),
                    4,
                    5,
                    0.3f,
                    0,
                    1,
                    2,
                    2
                )
            )

            addAnimation(
                "run",
                AnimatedSpriteSheet(
                    Core.assets.get("platformer/player.png", Texture::class.java),
                    4,
                    5,
                    0.1f,
                    0,
                    0,
                    4,
                    1
                )
            )

            addAnimation(
                "shoot",
                AnimatedSpriteSheet(
                    Core.assets.get("platformer/player.png", Texture::class.java),
                    4,
                    5,
                    0.3f,
                    0,
                    2,
                    2,
                    3
                )
            )

            addAnimation(
                "jump",
                AnimatedSpriteSheet(
                    Core.assets.get("platformer/player.png", Texture::class.java),
                    4,
                    5,
                    0.3f,
                    0,
                    3,
                    2,
                    4
                )
            )

            play("idle")
        }

        obj.attach<BoxShape>().apply {
            width = 16
            height = 16
        }

        obj.attach<DynamicBody>()

        obj.attach<PlayerController>()
    }
}

class PlayerController: Component() {
    private lateinit var body: DynamicBody
    private lateinit var renderer: AnimationRenderer

    private val gravity = -400f
    private val speed = 100f
    private val jumpSpeed = 100f
    private var jumps = 2
    private var jumping = false

    override fun init() {
        body = gameObject.getComponent<DynamicBody>()!!
        renderer = gameObject.getComponent<AnimationRenderer>()!!
    }

    override fun update(delta: Float) {
        val velocity = body.velocity

        velocity.y += gravity * delta

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            velocity.x = -speed
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            velocity.x = speed
        } else {
            velocity.x = 0f
        }

        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && jumps > 0) {
            renderer.play("jump")
            velocity.y = jumpSpeed
        }

        if (!jumping) {
            if (velocity.x != 0f) {
                renderer.play("run")
                renderer.flipH = velocity.x < 0
            } else {
                renderer.play("idle")
            }
        }

        body.moveAndSlide()
    }
}