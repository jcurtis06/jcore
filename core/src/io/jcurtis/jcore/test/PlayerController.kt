package io.jcurtis.jcore.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.Component

class PlayerController(gameObject: GameObject) : Component(gameObject) {
    override fun update(delta: Float) {
        if (Gdx.input.isKeyPressed(Keys.D)) {
            transform!!.x += 1f
        }

        println("player position: ${transform!!.position}")
    }

    override fun init() {
    }
}