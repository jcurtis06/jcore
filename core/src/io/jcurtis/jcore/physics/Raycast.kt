package io.jcurtis.jcore.physics

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.BoxCollider

class Raycast {
    fun cast(from: Vector2, to: Vector2): List<BoxCollider> {
        val collisions = mutableListOf<BoxCollider>()

        for (boxCollider in Core.colliders) {
            if (Intersector.intersectSegmentRectangle(from, to, boxCollider.rectangle)) {
                collisions.add(boxCollider)
            }
        }

        return collisions
    }
}