package dev.tran.nam.sortalgorithm.widget

import android.graphics.PointF
import android.graphics.RectF
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuCircle(
    val centerPoint: PointF,
    val startTextPoint: PointF,
    val mText : String,
    val rectF: RectF,
    val startAngle: Float = 0F,
    var isDrawing: Boolean = false,
    var isDraw: Boolean = false
) : Parcelable