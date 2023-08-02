package io.jcurtis.platformer.graphics

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class AnimatedSpriteSheet(
    var res: Texture,
    var cols: Int,
    var rows: Int,
    var duration: Float,
    var startX: Int = 0,
    var startY: Int = 0,
    var endX: Int = 0,
    var endY: Int = 0
) {
    fun splitAnimation(): Array<TextureRegion> {
        val tmp = TextureRegion.split(
            res,
            res.width / cols,
            res.height / rows
        )

        val frames = Array((endX - startX) * (endY - startY)) { TextureRegion() }
        var index = 0

        for (i in startY until endY) {
            for (j in startX until endX) {
                frames[index++] = tmp[i][j]
            }
        }

        return frames
    }
}