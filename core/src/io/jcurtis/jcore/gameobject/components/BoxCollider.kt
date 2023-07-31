package io.jcurtis.jcore.gameobject.components

import com.badlogic.gdx.math.Rectangle
import io.jcurtis.jcore.core.Core


class BoxCollider: Component() {
    var rectangle = Rectangle()
    var width = 0f
    var height = 0f

    var isTrigger = false

    init {
        Core.colliders.add(this)
        rectangle.width = width
        rectangle.height = height
    }

    fun getTop(): Float {
        return transform.position.y + height
    }

    fun getBottom(): Float {
        return transform.position.y
    }

    fun getLeft(): Float {
        return transform.position.x
    }

    fun getRight(): Float {
        return transform.position.x + width
    }

    override fun update(delta: Float) {
        rectangle.x = transform.position.x
        rectangle.y = transform.position.y
    }

    override fun init() {
        return
    }
}