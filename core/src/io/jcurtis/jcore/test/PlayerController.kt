package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.JCoreGame
import io.jcurtis.jcore.gameobject.components.AnimationRenderer
import io.jcurtis.jcore.gameobject.components.BoxCollider
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.RigidBody

class PlayerController : Component() {
    private var speed = 100
    private var rigidbody: RigidBody? = null
    lateinit var animation: AnimationRenderer

    private val mousePosInGameWorld: Vector2 = Vector2()

    override fun init() {
        rigidbody = gameObject.getComponent<RigidBody>()
        animation = gameObject.getComponent<AnimationRenderer>()!!
    }

    override fun update(delta: Float) {
        val velocity = rigidbody!!.velocity

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

        if (Gdx.input.isKeyPressed(Keys.Q)) {
            transform.position.setZero()
        }

        if (velocity.x != 0f) {
            animation.flipH = (velocity.x < 0)
            animation.play("walk")
        } else {
            animation.play("idle")
        }

        updateCameraPosition()

        rigidbody?.moveAndSlide(velocity)
        velocity.set(0f, 0f)
    }

    private fun updateCameraPosition() {
        // Calculate the center of the screen in game world coordinates
        val playerWeight = 3f
        val center = Core.getLocalMouse()
        println(Core.getLocalMouse())
        center.add(transform.position.cpy().scl(playerWeight)).scl(1/(playerWeight+1))

        // Update the camera position
        Core.camera.position.set(center.x, center.y, 0f)
        Core.camera.update()
    }
}