package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import io.jcurtis.jcore.gameobject.components.Component

class BoxShape : Component(), ShapeComponent {
    var width = 0
    var height = 0

    private val collider = PolygonShape()

    override fun getCollider(): Shape {
        collider.setAsBox(width.toFloat()/2, height.toFloat()/2, Vector2(width.toFloat()/2, height.toFloat()/2), 0f)
        return collider
    }

    override fun update(delta: Float) = Unit
    override fun init() = Unit
}