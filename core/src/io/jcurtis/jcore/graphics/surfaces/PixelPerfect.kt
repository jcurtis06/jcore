package io.jcurtis.jcore.graphics.surfaces

import io.jcurtis.jcore.graphics.Surface

class PixelPerfect(resWidth: Int, resHeight: Int) : Surface() {
    init {
        setSize(resWidth, resHeight, true)
    }
}