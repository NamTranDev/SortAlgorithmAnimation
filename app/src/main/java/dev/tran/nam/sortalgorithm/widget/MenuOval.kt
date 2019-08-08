package dev.tran.nam.sortalgorithm.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import tran.nam.Logger
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class MenuOval : View, Animator.AnimatorListener {

    private lateinit var mPaintOval: Paint
    private lateinit var mPaintPoint: Paint
    private lateinit var mRectOval: RectF
    private val mDistanceHalfHeight = 150F
    private var mSweepAngle = 0F
    private var mSweepAngleChild = 0F
    private val mAngleCirleMain = 180F / 5
    private lateinit var mPathArc: Path
    private val mListPoint = ArrayList<PointF>()
    private var mCenterCircleMain = PointF()
    private var mRadiusCircleMain = 0.0F
    private var mRadiusCircleChild = 100F
    private var isDrawCirCleChild = false
    private var mPositionCirCleChild = 1

    private var mAngleDistanceCircleChild = 0F
    private lateinit var mAnimatorDrawCircleMain: ValueAnimator
    private lateinit var mAnimatorDrawCircleChild: ValueAnimator

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    fun init() {

        mRectOval = RectF()
        mPathArc = Path()

        mPaintOval = Paint()
        mPaintOval.strokeWidth = 5f
        mPaintOval.isAntiAlias = true
        mPaintOval.color = Color.BLACK
        mPaintOval.style = Paint.Style.STROKE
        mPaintOval.strokeCap = Paint.Cap.ROUND

        mPaintPoint = Paint()
        mPaintPoint.color = Color.RED
        mPaintPoint.style = Paint.Style.FILL
        mPaintPoint.isAntiAlias = true

        mAnimatorDrawCircleChild = ValueAnimator.ofFloat(0F, 360F)
        mAnimatorDrawCircleChild.addUpdateListener {
            mSweepAngleChild = it.animatedValue as Float
            invalidate()
        }
        mAnimatorDrawCircleChild.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                isDrawCirCleChild = false
                Logger.debug("mPositionCirCleChild : $mPositionCirCleChild")
                val angleStart = mAngleCirleMain * (mPositionCirCleChild - 1) - mAngleDistanceCircleChild
                val angleEnd = mAngleCirleMain * mPositionCirCleChild - mAngleDistanceCircleChild
                Logger.debug("mPositionCirCleChild : $angleStart")
                Logger.debug("mPositionCirCleChild : $angleEnd")
                animationDrawCircleMain(angleStart, angleEnd)
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })
    }

    fun drawView() {
        mAnimatorDrawCircleMain.start()
    }

    private fun animationDrawCircleMain(start: Float, end: Float,isStart : Boolean = true) {
        mAnimatorDrawCircleMain = ValueAnimator.ofFloat(start, end)
        mAnimatorDrawCircleMain.duration = 500
        mAnimatorDrawCircleMain.addUpdateListener {
            mSweepAngle = it.animatedValue as Float
            invalidate()
        }
        mAnimatorDrawCircleMain.addListener(this)
        if (isStart)
            mAnimatorDrawCircleMain.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mRadiusCircleMain = h / 2 - mDistanceHalfHeight

        mRectOval.set(
            0 - h / 2 + mDistanceHalfHeight, mDistanceHalfHeight
            , mRadiusCircleMain, h - mDistanceHalfHeight
        )

        Logger.debug(mRectOval)
        Logger.debug(mRectOval.centerX(), mRectOval.centerY())
        mCenterCircleMain.set(mRectOval.centerX(), mRectOval.centerY())


        Logger.debug(mAngleCirleMain)
        Logger.debug(mAngleCirleMain * 2)
        Logger.debug(mAngleCirleMain * 3)
        Logger.debug(mAngleCirleMain * 4)

        val point = getPosition(
            PointF(0F, h / 2.toFloat()), h / 2 - mDistanceHalfHeight
            , mAngleCirleMain - 90
        )

        mListPoint.add(point)
        mListPoint.add(
            getPosition(
                PointF(0F, h / 2.toFloat()), h / 2 - mDistanceHalfHeight
                , mAngleCirleMain * 2 - 90
            )
        )
        mListPoint.add(
            getPosition(
                PointF(0F, h / 2.toFloat()), h / 2 - mDistanceHalfHeight
                , mAngleCirleMain * 3 - 90
            )
        )
        mListPoint.add(
            getPosition(
                PointF(0F, h / 2.toFloat()), h / 2 - mDistanceHalfHeight
                , mAngleCirleMain * 4 - 90
            )
        )

        Logger.debug(mListPoint)


        Logger.debug(mCenterCircleMain)
        Logger.debug(point)

        val d: Float
        val a: Float
        val hi: Float

        d = distance(mCenterCircleMain, point)
        a = (mRadiusCircleMain * mRadiusCircleMain - mRadiusCircleChild * mRadiusCircleChild + d * d) / (2 * d)
        hi = sqrt(mRadiusCircleMain * mRadiusCircleMain - a * a)

        val xIntersection = point.x + hi * (point.y - mCenterCircleMain.y) / d       // also x3=x2-h*(y1-y0)/d
        val yIntersection = point.y - hi * (point.x - mCenterCircleMain.x) / d       // also y3=y2+h*(x1-x0)/d

        val pointIntersection = PointF(xIntersection, yIntersection)
        Logger.debug(pointIntersection)
        mAngleDistanceCircleChild = 180 - angle(mCenterCircleMain, pointIntersection)
        mAngleDistanceCircleChild = mAngleCirleMain - mAngleDistanceCircleChild

        Logger.debug(mAngleDistanceCircleChild)

        animationDrawCircleMain(0F, mAngleCirleMain - mAngleDistanceCircleChild,false)
    }

    fun distance(p1: PointF, p2: PointF): Float {
        return sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y))
    }

    fun angle(circlePoint: PointF, point: PointF): Float {
        val angle =
            Math.toDegrees(Math.atan2((circlePoint.y - point.y).toDouble(), (point.x - circlePoint.x).toDouble())) + 90
        return (if (angle <= 180) angle else angle - 360).toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawArc(mRectOval, 270f, mSweepAngle, true, mPaintOval)
//        canvas?.drawArc(mRectOval, 270f, mAngleCirleMain, true, mPaintOval)
//        canvas?.drawArc(mRectOval, 270f, mAngleCirleMain * 2, true, mPaintOval)
//        canvas?.drawArc(mRectOval, 270f, mAngleCirleMain * 3, true, mPaintOval)
//        canvas?.drawArc(mRectOval, 270f, mAngleCirleMain * 4, true, mPaintOval)
//        canvas?.drawRect(mRectOval, mPaintOval)
//        canvas?.drawCircle(mCenterCircleMain.x, mCenterCircleMain.y, mRadiusCircleMain, mPaintOval)

        Logger.debug(isDrawCirCleChild)
        Logger.debug(mPositionCirCleChild)
        if (isDrawCirCleChild) {
            for ((index,point) in mListPoint.withIndex()){
                if (index < mPositionCirCleChild){
                    if (index == mPositionCirCleChild - 1){
                        val rect = RectF(point.x - mRadiusCircleChild,point.y - mRadiusCircleChild
                            ,point.x + mRadiusCircleChild,point.y + mRadiusCircleChild)
                        canvas?.drawArc(rect, 270f, mSweepAngleChild, true, mPaintPoint)
                    }else{
                        canvas?.drawCircle(point.x, point.y, mRadiusCircleChild, mPaintPoint)
                    }
                }
            }
        } else {
            for ((index,point) in mListPoint.withIndex()){
                if (index < mPositionCirCleChild){
                    canvas?.drawCircle(point.x, point.y, mRadiusCircleChild, mPaintPoint)
                }
            }
        }
    }

    private fun getPosition(center: PointF, radius: Float, angle: Float): PointF {

        return PointF(
            (center.x + radius * cos(Math.toRadians(angle.toDouble()))).toFloat(),
            (center.y + radius * sin(Math.toRadians(angle.toDouble()))).toFloat()
        )
    }

    override fun onAnimationEnd(p0: Animator?) {
        isDrawCirCleChild = true
        mSweepAngleChild = 0F
        mAnimatorDrawCircleChild.start()
    }

    override fun onAnimationStart(p0: Animator?) {

    }

    override fun onAnimationRepeat(p0: Animator?) {

    }

    override fun onAnimationCancel(p0: Animator?) {

    }
}