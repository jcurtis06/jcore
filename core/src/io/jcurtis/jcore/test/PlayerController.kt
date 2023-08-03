package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.graphics.Camera
import io.jcurtis.jcore.gameobject.components.physics.DynamicBody

class PlayerController : Component() {
    private var speed = 100
    private var rigidbody: DynamicBody? = null

    lateinit var camera: Camera

    override fun init() {
        rigidbody = gameObject.getComponent<DynamicBody>()
        camera = Main.camera.getComponent<Camera>()!!
    }

    override fun update(delta: Float) {
        val velocity = rigidbody!!.velocity
        velocity.setZero()

        if (Gdx.input.isKeyPressed(Keys.D)) {
            velocity.x += speed
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            velocity.x -= speed
        }

        if (Gdx.input.isKeyPressed(Keys.W)) {
            velocity.y += speed
        }

        if (Gdx.input.isKeyPressed(Keys.S)) {
            velocity.y -= speed
        }

        rigidbody?.moveAndSlide()
        camera.setCenterSmooth(transform.position)
    }
}