package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.jcurtis.jcore.gameobject.components.BoxCollider
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.RigidBody

class PlayerController : Component() {
    private var speed = 100
    private var rigidbody: RigidBody? = null

    override fun init() {
        rigidbody = gameObject.getComponent<RigidBody>()
    }

    override fun update(delta: Float) {
        val velocity = rigidbody!!.velocity
        velocity.setZero()

        if (Gdx.input.isKeyPressed(Keys.D)) {
            velocity.x += speed*delta
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            velocity.x -= speed*delta
        }

        if (Gdx.input.isKeyPressed(Keys.W)) {
            velocity.y += speed*delta
        }

        if (Gdx.input.isKeyPressed(Keys.S)) {
            velocity.y -= speed*delta
        }

        if (Gdx.input.isKeyPressed(Keys.Q)) {
            transform.position.setZero()
        }

        rigidbody?.moveAndSlide()
    }
}