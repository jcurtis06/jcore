package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.Component

@Suppress("unused", "MemberVisibilityCanBePrivate")
class StaticBody(
    override var onEnter: (GameObject) -> Unit = {},
    override var onExit: (GameObject) -> Unit = {},
    override var onStay: (GameObject) -> Unit = {}
) : Component(), CollisionListener {
    var collider = PolygonShape()

    var friction = 0.0f
    var bounce = 0.0f
    var density = 0.0f

    lateinit var fixture: FixtureDef
    lateinit var body: Body

    override fun init() {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position.set(transform.position)
        body = Core.world.createBody(bodyDef)

        fixture = FixtureDef()
        fixture.shape = collider
        fixture.friction = friction
        fixture.restitution = bounce
        fixture.density = density
        body.createFixture(fixture)
        body.userData = gameObject

        collider.dispose()
    }

    override fun update(delta: Float) {
        body.position.set(transform.position)
    }
}