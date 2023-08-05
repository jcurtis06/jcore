package io.jcurtis.jcore.core

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.physics.CollisionListener

/**
 * Handles forwarding collision events from Box2D to GameObjects
 * @see CollisionListener
 */
class ComponentContactForwarding : ContactListener {
    override fun beginContact(contact: Contact?) {
        val a = contact?.fixtureA?.body?.userData
        val b = contact?.fixtureA?.body?.userData

        if (a is GameObject && b is GameObject) {
            a.getComponentLoosely<CollisionListener>()?.onEnter?.let { it(b) }
            b.getComponentLoosely<CollisionListener>()?.onEnter?.let { it(a) }
        }
    }

    override fun endContact(contact: Contact?) {
        val a = contact?.fixtureA?.body?.userData
        val b = contact?.fixtureA?.body?.userData

        if (a is GameObject && b is GameObject) {
            a.getComponentLoosely<CollisionListener>()?.onExit?.let { it(b) }
            b.getComponentLoosely<CollisionListener>()?.onExit?.let { it(a) }
        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        val a = contact?.fixtureA?.body?.userData
        val b = contact?.fixtureA?.body?.userData

        if (a is GameObject && b is GameObject) {
            a.getComponentLoosely<CollisionListener>()?.onStay?.let { it(b) }
            b.getComponentLoosely<CollisionListener>()?.onStay?.let { it(a) }
        }
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) = Unit
}