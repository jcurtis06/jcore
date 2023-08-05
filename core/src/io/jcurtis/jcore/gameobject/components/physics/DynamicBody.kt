package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.GameObject
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
class DynamicBody(
    override var onEnter: (GameObject) -> Unit = {},
    override var onExit: (GameObject) -> Unit = {},
    override var onStay: (GameObject) -> Unit = {}
) : Component(), CollisionListener {
    var velocity = Vector2()
    var friction = 0.0f
    var bounce = 0.0f
    var density = 0.0f
    var gravityScale = 0.0f

    var collidedDirections = CollidedDirections()

    lateinit var fixture: FixtureDef
    lateinit var body: Body

    lateinit var collider: ShapeComponent

    override fun init() {
        try {
            collider = gameObject.getComponent<BoxShape>() ?: gameObject.getComponent<CircleShape>()!!
        } catch (e: Exception) {
            throw Exception("DynamicBody component requires a ShapeComponent.")
        }

        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(transform.position)
        body = Core.world.createBody(bodyDef)

        fixture = FixtureDef()
        fixture.shape = collider.getCollider()
        fixture.friction = friction
        fixture.restitution = bounce
        fixture.density = density
        body.createFixture(fixture)

        body.userData = gameObject

        collider.getCollider().dispose()
    }

    fun moveAndSlide() {
        body.linearVelocity = velocity
        transform.position.set(body.position.x.roundToInt().toFloat(), body.position.y.roundToInt().toFloat())
    }

    override fun update(delta: Float) = Unit
}

data class CollidedDirections(
    var up: Boolean = false,
    var down: Boolean = false,
    var left: Boolean = false,
    var right: Boolean = false
)
