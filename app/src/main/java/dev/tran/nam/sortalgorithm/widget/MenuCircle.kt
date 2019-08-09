package dev.tran.nam.sortalgorithm.widget

import android.graphics.BitmapShader
import android.graphics.PointF
import android.graphics.RectF

data class MenuCircle(
    val centerPoint: PointF,
    val rectF: RectF,
    val startAngle: Float = 0F,
    val shader: BitmapShader? = null,
    var isDrawing: Boolean = false,
    var isDraw: Boolean = false
)