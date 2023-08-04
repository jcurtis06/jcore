package io.jcurtis.jcore.core

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import io.jcurtis.jcore.gameobject.GameObject
import io.jcurtis.jcore.gameobject.components.graphics.Image
import io.jcurtis.jcore.graphics.Renderable

/**
 * A singleton class that holds all the game's objects, colliders, images, and renderables.
 * Also holds useful global variables such as the current camera and the asset manager.
 */
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
}