package dev.tran.nam.sortalgorithm.view

import android.graphics.Path
import android.graphics.Point

class PivotPointView {
    var pointText: Point? = null
        private set
    var firstLine: Point? = null
        private set
    var lastLine: Point? = null
        private set
    var pathArrow: Path? = null
        private set

    init {
        init()
    }

    private fun init() {
        pointText = Point()
        firstLine = Point()
        lastLine = Point()
        pathArrow = Path()
    }

    fun setPoint(x: Int, y: Int) {
        pointText!!.set(x, y)
        val yArrow = y - 40
        val yArrowPeak = yArrow - 70
        firstLine!!.set(x, yArrow)
        lastLine!!.set(x, yArrowPeak + 5)
        pathArrow!!.moveTo(x.toFloat(), yArrowPeak.toFloat())
        pathArrow!!.lineTo((x - 10).toFloat(), (yArrowPeak + 10).toFloat())
        pathArrow!!.lineTo((x + 10).toFloat(), (yArrowPeak + 10).toFloat())
        pathArrow!!.lineTo(x.toFloat(), yArrowPeak.toFloat())
    }

    fun clear() {
        pathArrow!!.reset()
    }
}