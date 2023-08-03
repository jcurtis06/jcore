package io.jcurtis.jcore.gameobject.components.physics

import com.badlogic.gdx.physics.box2d.PolygonShape

/**
 * A component that allows a GameObject to interact with other objects.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class Collider {
    var shape = PolygonShape()
}