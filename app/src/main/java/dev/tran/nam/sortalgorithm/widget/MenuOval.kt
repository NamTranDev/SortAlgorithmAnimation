package dev.tran.nam.sortalgorithm.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class MenuOval : View {

    private lateinit var mPaintOval: Paint
    private lateinit var mRectOval : Rect
    private val mDistanceHalfWidth = 100
    private val mDistanceHalfHeight = 300

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    fun init(){

        mRectOval = Rect()

        mPaintOval = Paint()
        mPaintOval.strokeWidth = 5f
        mPaintOval.isAntiAlias = true
        mPaintOval.color = Color.BLACK
        mPaintOval.style = Paint.Style.STROKE
        mPaintOval.strokeCap = Paint.Cap.ROUND

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mRectOval.contains(0 - mDistanceHalfWidth,height/2 - mDistanceHalfHeight,0 + mDistanceHalfWidth,height/2 + mDistanceHalfHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(mRectOval,mPaintOval);
    }

}