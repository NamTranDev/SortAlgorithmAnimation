package dev.tran.nam.sortalgorithm.widget

import android.graphics.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuCircle(
    val centerPoint: PointF,
    val startTextPoint: PointF,
    val mText : String,
    val rectIcon: RectF,
    val rectText: RectF,
    val startAngle: Float = 0F,
    var isDrawing: Boolean = false,
    var isDraw: Boolean = false
) : Parcelable {

    var mIcon : Bitmap? = null
}