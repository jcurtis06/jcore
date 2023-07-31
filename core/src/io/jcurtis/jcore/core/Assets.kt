package io.jcurtis.jcore.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

object Assets {
    private var loaded = mutableMapOf<String, FileHandle>()

    fun load(path: String): FileHandle {
        val file = Gdx.files.internal(path)
        loaded[path] = file
        return file
    }

    fun get(path: String): FileHandle? {
        return loaded[path]
    }
}