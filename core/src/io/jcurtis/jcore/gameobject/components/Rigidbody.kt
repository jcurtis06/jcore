package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.utils.Logger
import io.jcurtis.jcore.core.Core

class Rigidbody: Component() {
    var collider: BoxCollider? = null

    override fun init() {
        collider = gameObject.getComponent<BoxCollider>()

        if (collider == null) {
            Logger("Rigidbody", Logger.DEBUG).debug("Rigidbody component requires a BoxCollider component")
        }
    }

    override fun update(delta: Float) {
        if (collider == null) return

        Core.colliders.forEach { c ->
            if (c.rectangle.overlaps(collider!!.rectangle)) {
            }
        }
    }
}