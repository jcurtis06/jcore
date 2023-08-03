package io.jcurtis.jcore.test

import com.badlogic.gdx.graphics.Texture
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.graphics.AnimationRenderer
import io.jcurtis.jcore.gameobject.components.physics.BoxCollider
import io.jcurtis.jcore.gameobject.components.physics.RigidBody
import io.jcurtis.platformer.graphics.AnimatedSpriteSheet

class Player {
    val obj = GameObject()

    init {
        obj.attach<PlayerController>()
        obj.attach<AnimationRenderer>().apply {
            addAnimation(
                "idle",
                AnimatedSpriteSheet(
                    Core.assets.get("player.png", Texture::class.java),
                    7,
                    1,
                    0.1f,
                    0,
                    0,
                    3,
                    1
                )
            )

            addAnimation(
                "walk",
                AnimatedSpriteSheet(
                    Core.assets.get("player.png", Texture::class.java),
                    7,
                    1,
                    0.1f,
                    4,
                    0,
                    7,
                    1
                )
            )
        }
        obj.attach<BoxCollider>().apply {
            width = 14f
            height = 12f
        }
        obj.attach<RigidBody>()

        obj.getComponent<AnimationRenderer>()?.play("idle")
        obj.getComponent<AnimationRenderer>()?.flipH = true
    }
}