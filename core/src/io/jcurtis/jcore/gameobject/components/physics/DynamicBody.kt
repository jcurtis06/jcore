package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component

/**
 * A component that allows the game object to collide with other objects.
 * @property velocity The velocity of the game object to be applied in [moveAndSlide].
 * @property collidingDirection The direction that the game object is colliding in.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class DynamicBody() : Component() {
    var velocity = Vector2()
    var collider: Shape = PolygonShape()
    var friction = 0.0f
    var bounce = 0.0f
    var density = 0.0f
    var gravityScale = 0.0f

    lateinit var fixture: FixtureDef
    lateinit var body: Body

    override fun init() {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(transform.position)
        body = Core.world.createBody(bodyDef)

        fixture = FixtureDef()
        fixture.shape = collider
        fixture.friction = friction
        fixture.restitution = bounce
        fixture.density = density
        body.createFixture(fixture)


        collider.dispose()
    }

    override fun update(delta: Float) {
        transform.position = body.position

        println(body.linearDamping)
        println(body.angularDamping)
    }

    fun moveAndSlide() {
        // move our position based on our velocity
        // move our rigidbody
        body.linearVelocity = velocity
    }
}
