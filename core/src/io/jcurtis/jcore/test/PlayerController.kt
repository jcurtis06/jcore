package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.Image

class PlayerController : Component() {
    private var image: Image? = null

    override fun init() {
        image = gameObject.getComponent<Image>()
    }

    override fun postInit() {
        println("postinit player")
    }

    override fun update(delta: Float) {
        if (Gdx.input.isKeyPressed(Keys.D)) {
            transform.x += 1f
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            transform.x -= 1f
        }

        if (Gdx.input.isKeyPressed(Keys.W)) {
            transform.y += 1f
        }

        if (Gdx.input.isKeyPressed(Keys.S)) {
            transform.y -= 1f
        }

        if (Gdx.input.isKeyPressed(Keys.Q)) {
            image!!.rotation += 1f
        }
    }
}