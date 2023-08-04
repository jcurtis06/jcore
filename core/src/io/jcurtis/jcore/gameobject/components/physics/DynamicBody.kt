package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import kotlin.math.roundToInt

/**
 * A component that allows the game object to collide with other objects.
 * @property velocity The velocity of the game object to be applied in [moveAndSlide].
 * @property collider The shape of the collider.
 * @property friction The friction of the collider.
 * @property bounce The bounce of the collider.
 * @property density The density of the collider.
 * @property gravityScale The gravity scale of the collider.
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

    fun moveAndSlide() {
        body.linearVelocity = velocity
        transform.position.set(body.position.x.roundToInt().toFloat(), body.position.y.roundToInt().toFloat())
    }

    override fun update(delta: Float) = Unit
}
