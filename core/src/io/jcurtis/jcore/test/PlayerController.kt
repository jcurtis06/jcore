package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.BoxCollider
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.Image
import io.jcurtis.jcore.gameobject.components.Rigidbody

class PlayerController : Component() {
    private var rigidbody: Rigidbody? = null

    override fun init() {
        rigidbody = gameObject.getComponent<Rigidbody>()
    }

    override fun update(delta: Float) {
        val velocity = rigidbody!!.velocity

        if (Gdx.input.isKeyPressed(Keys.D)) {
            velocity.x += 1f
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            velocity.x -= 1f
        }

        if (Gdx.input.isKeyPressed(Keys.W)) {
            velocity.y += 1f
        }

        if (Gdx.input.isKeyPressed(Keys.S)) {
            velocity.y -= 1f
        }

        rigidbody?.moveAndSlide(velocity)
        velocity.set(0f, 0f)
    }
}