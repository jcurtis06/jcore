package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.graphics.Camera
import io.jcurtis.jcore.gameobject.components.physics.RigidBody
import io.jcurtis.jcore.gameobject.components.graphics.SmoothedCamera

class PlayerController : Component() {
    private var speed = 100
    private var rigidbody: RigidBody? = null

    lateinit var camera: Camera

    override fun init() {
        rigidbody = gameObject.getComponent<RigidBody>()
        camera = Main.camera.getComponent<Camera>()!!
    }

    override fun update(delta: Float) {
        val velocity = rigidbody!!.velocity
        velocity.setZero()

        if (Gdx.input.isKeyPressed(Keys.D)) {
            velocity.x += speed * delta
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            velocity.x -= speed * delta
        }

        if (Gdx.input.isKeyPressed(Keys.W)) {
            velocity.y += speed * delta
        }

        if (Gdx.input.isKeyPressed(Keys.S)) {
            velocity.y -= speed * delta
        }

        rigidbody?.moveAndSlide()
        //Core.currentCamera.position.set(transform.position.x, transform.position.y, 0f)
        camera.smoothingEnabled = true
        camera.setCenterSmooth(transform.position)
        println("Player position: ${transform.position}")
        println("Camera position: ${Main.gameCamera.position}")
    }
}