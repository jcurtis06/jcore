package io.jcurtis.jcore.gameobject

import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.Transform

class GameObject {
    val components = mutableListOf<Component>()

    var transform: Transform? = null

    init {
        transform = attach<Transform>()
        Core.objects.add(this)
    }

    fun update(delta: Float) {
        components.forEach { it.update(delta) }
    }

    fun init() {
        components.forEach { it.init() }
    }

    inline fun <reified T : Component> attach(): T {
        val component = T::class.java.getConstructor(GameObject::class.java).newInstance(this)

        if (component is Transform) transform = component
        if (transform == null) println("Transform is null! Component will not be mounted.").also { return component }
        if (component !is Transform) component.transform = transform!!

        components.add(component)
        return component
    }

    inline fun <reified T : Component> detach() {
        components.forEach {
            if (it is T) {
                components.remove(it)
            }
        }
    }

    inline fun <reified T : Component> getComponent(): T? {
        return components.find { it is T } as T?
    }
}