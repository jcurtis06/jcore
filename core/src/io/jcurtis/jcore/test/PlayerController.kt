package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.*
import kotlin.math.roundToInt

class PlayerController : Component() {
    private var speed = 100
    private var rigidbody: RigidBody? = null
    lateinit var image: Image

    override fun init() {
        rigidbody = gameObject.getComponent<RigidBody>()
        image = gameObject.getComponent<Image>()!!
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
            image.rotation += 1
        }

        rigidbody?.moveAndSlide()
        Main.camera.getComponent<SmoothedCamera>()?.setTarget(transform.position.x.toInt(), transform.position.y.toInt())
    }
}