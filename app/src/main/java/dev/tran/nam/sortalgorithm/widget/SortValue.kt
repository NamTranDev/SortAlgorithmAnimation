package dev.tran.nam.sortalgorithm.widget

import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SortValue(
    var rect: Rect? = null,
    var value: Int = 0,
    var isChooseExplain: Boolean = false,
    var isCurrentPosition: Boolean = false,
    var isSort: Boolean = false
) : Parcelable {

    var mPointTextChoose: Point = Point()
    var mFirstCurrentLine: Point = Point()
    var mFirstChooseLine: Point = Point()
    var mLastCurrentLine: Point = Point()
    var mLastChooseLine: Point = Point()
    var mPathCurrent : Path = Path()
    var mPathChoose : Path = Path()

    fun setPoint(x: Int, y: Int,isCurrent : Boolean = true) {
        val yArrow = y - 40
        val yArrowPeak = yArrow - 70
        if (isCurrent){
            mFirstCurrentLine.set(x, yArrow)
            mLastCurrentLine.set(x, yArrowPeak)
            mPathCurrent.moveTo(x.toFloat(), yArrow.toFloat())
            mPathCurrent.lineTo((x - 10).toFloat(), (yArrow - 10).toFloat())
            mPathCurrent.lineTo((x + 10).toFloat(), (yArrow - 10).toFloat())
            mPathCurrent.lineTo(x.toFloat(), yArrow.toFloat())
        }else{
            mPointTextChoose.set(x,yArrowPeak - 10)
            mFirstChooseLine.set(x, yArrow)
            mLastChooseLine.set(x, yArrowPeak)
            mPathChoose.moveTo(x.toFloat(), yArrow.toFloat())
            mPathChoose.lineTo((x - 10).toFloat(), (yArrow - 10).toFloat())
            mPathChoose.lineTo((x + 10).toFloat(), (yArrow - 10).toFloat())
            mPathChoose.lineTo(x.toFloat(), yArrow.toFloat())
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}