package io.jcurtis.jcore.gameobject

import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.gameobject.components.Component
import io.jcurtis.jcore.gameobject.components.Transform
import io.jcurtis.jcore.graphics.Renderable

/**
 * GameObject is the base class for all objects in the game.
 * It contains a list of components that can be attached to it.
 */
@Suppress("unused")
class GameObject {
    /** Holds all the components. **DO NOT DIRECTLY EDIT** */
    val components = mutableListOf<Component>()

    /** Transform is the only Component that is attached to GameObject */
    var transform: Transform? = null

    /** Attach the Transform to this GameObject when created */
    init {
        transform = attach<Transform>()
        Core.objectsToAdd.add(this)
    }

    /** Update all the components attached to the GameObject */
    fun update(delta: Float) {
        components.forEach { it.update(delta) }
    }

    /** Initialize all the components attached to the GameObject */
    fun init() {
        println("init")
        components.forEach { it.init() }
    }

    fun postInit() {
        println("post init")
        components.forEach { it.postInit() }
    }

    /**
     * Attach a component to the GameObject. There can only be one of each component type attached to a GameObject.
     * Must be called **after** `GameObject.init`
     * @param T The component to attach.
     * @return The component that was attached.
     */
    inline fun <reified T : Component> attach(): T {
        val component = T::class.java.getDeclaredConstructor().newInstance()

        if (component is Transform) transform = component
        if (transform == null) println("Transform is null! Component will not be mounted.").also { return component }
        component.gameObject = this
        if (component !is Transform) component.transform = transform!!

        if (component is Renderable) Core.renderables.add(component)

        components.add(component)
        return component
    }

    /**
     * Detach a component from the GameObject.
     * @param T The component to detach.
     */
    inline fun <reified T : Component> detach() {
        components.forEach {
            if (it is T) {
                components.remove(it)
            }
        }
    }

    /**
     * Get a component from the GameObject.
     * @param T The component to get.
     * @return The component that was found.
     */
    inline fun <reified T : Component> getComponent(): T? {
        return components.find { it is T } as T?
    }
}