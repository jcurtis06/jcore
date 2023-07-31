package io.jcurtis.jcore.core.modules

import com.badlogic.gdx.ApplicationAdapter
import io.jcurtis.jcore.core.Core
import io.jcurtis.jcore.core.Graphics

/**
 * The core of the rendering engine. Handles the creation of modules and their lifecycle.
 * Basically a wrapper for [ApplicationAdapter] that allows for easier and cleaner usage.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class ModuleCore: ApplicationAdapter() {
    private var modules = mutableListOf<Module>()

    abstract fun init()

    fun preInit() {
    }

    fun postInit() {
    }

    fun update() {
    }

    protected fun attach(module: Module) {
        modules.add(module)
        module.preInit()
    }

    override fun resize(width: Int, height: Int) {
        modules.forEach { m -> m.resize(width, height) }
    }

    override fun create() {
        init()
        preInit()
        modules.forEach { m -> m.init() }
        postInit()
    }

    override fun render() {
        modules.forEach { m -> m.update() }
        update()
    }

    override fun pause() {
        modules.forEach { m -> m.pause() }
    }

    override fun resume() {
        modules.forEach { m -> m.resume() }
    }

    override fun dispose() {
        modules.forEach { m -> m.dispose() }
        Core.dispose()
    }
}