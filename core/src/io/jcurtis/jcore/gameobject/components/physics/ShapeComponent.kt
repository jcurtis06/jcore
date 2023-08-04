package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.physics.box2d.Shape

interface ShapeComponent {
    fun getCollider(): Shape
}