package io.jcurtis.jcore.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.graphics.Image
import io.jcurtis.jcore.gameobject.components.physics.BoxCollider
import io.jcurtis.jcore.graphics.Renderable
import io.jcurtis.jcore.test.Main
object Core {
    var objects = mutableListOf<GameObject>()
    var objectsToAdd = mutableListOf<GameObject>()

    var images = mutableListOf<Image>()
    var renderables = mutableListOf<Renderable>()

    var world = World(Vector2(0f, 0f), true)

    // The current camera that the game is rendering with
    var currentCamera: OrthographicCamera = OrthographicCamera()

    // LibGDX's asset manager
    var assets = AssetManager()

    val resWidth = 320f
    val resHeight = 180f

    fun getGlobalMouse(): Vector2 {
        val unprojected = currentCamera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
        return Vector2(unprojected.x, unprojected.y)
    }

    fun getLocalMouse(): Vector2 {
        // return the mouse position within the window
        // will not register if the mouse is outside of the window
        val global = getGlobalMouse()
        global.x = MathUtils.clamp(global.x, -resWidth, resWidth)
        global.y = MathUtils.clamp(global.y, -resHeight, resHeight)

        return global
    }
}