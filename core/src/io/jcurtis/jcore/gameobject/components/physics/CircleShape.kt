package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.Shape
import io.jcurtis.jcore.gameobject.components.Component

class CircleShape : Component(), ShapeComponent {
    var radius = 0f

    private val collider = CircleShape()

    override fun getCollider(): Shape {
        collider.radius = radius/2
        collider.position = Vector2(radius/2, radius/2)
        return collider
    }

    override fun update(delta: Float) = Unit
    override fun init() = Unit
}