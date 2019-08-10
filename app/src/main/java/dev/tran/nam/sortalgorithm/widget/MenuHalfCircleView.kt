package dev.tran.nam.sortalgorithm.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import dev.tran.nam.sort.algorithm.R
import tran.nam.Logger
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class MenuHalfCircleView : View {

    private lateinit var mPaintOval: Paint
    private lateinit var mPaintPoint: Paint
    private lateinit var mPaintText: TextPaint
    private lateinit var mRectOval: RectF
    private val mDistanceTextToIcon = 0F
    private var mMenuItems = mutableListOf<MenuItem>()
    private var mMenuSize = 0
    private val mDistanceHalfHeight = 100F
    private var mSweepAngle = 0F
    private var mSweepAngleChild = 0F
    private var mPositionCharAt = 0
    private var mAngleCirleMain = 180F
    private var mListMenu = ArrayList<MenuCircle>()
    private var mCenterCircleMain = PointF()
    private var mRadiusCircleMain = 0.0F
    private var mRadiusCircleChild = 100F
    private var mPositionCirCleChild = 1
    private var mPathCircleMain = Path()
    private var mPathCircleChild = Path()
    private val mDuration = 500L

    private var mAngleDistanceCircleChild = 0F
    private lateinit var mAnimatorDrawCircleMain: ValueAnimator
    private lateinit var mAnimatorDrawCircleChild: ValueAnimator
    private lateinit var mAnimatorText: ValueAnimator
    var iMenuListener: IMenuListener? = null

//    private var isDrawMenu = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MenuHalfCircleView)
        typeArray.recycle()

        mRectOval = RectF()

        mPaintOval = Paint()
        mPaintOval.strokeWidth = 5f
        mPaintOval.isAntiAlias = true
        mPaintOval.color = Color.BLACK
        mPaintOval.style = Paint.Style.STROKE
        mPaintOval.strokeCap = Paint.Cap.ROUND

        mPaintPoint = Paint()
        mPaintPoint.isAntiAlias = true
        mPaintPoint.isFilterBitmap = true

        mPaintText = TextPaint()
        mPaintText.isAntiAlias = true
        mPaintText.isDither = true
        mPaintText.color = Color.BLACK
        mPaintText.strokeWidth = 15f
        mPaintText.textSize = 45f
        mPaintText.strokeJoin = Paint.Join.ROUND
        mPaintText.strokeCap = Paint.Cap.ROUND

        mAnimatorDrawCircleMain = ValueAnimator.ofFloat(0F, mAngleCirleMain)
        mAnimatorDrawCircleMain.duration = mDuration
        mAnimatorDrawCircleMain.addUpdateListener {
            mSweepAngle = it.animatedValue as Float
            invalidate()
        }
        mAnimatorDrawCircleMain.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                if (mPositionCirCleChild < mMenuSize /*&& !isDrawMenu*/) {
//                    isDrawMenu = true
                    val menu = mListMenu[mPositionCirCleChild - 1]
                    menu.isDrawing = true
                    mSweepAngleChild = 0F
                    mPositionCharAt = 0
                    mAnimatorText.setIntValues(0, menu.mText.length)
                    mAnimatorText.start()
                    mAnimatorDrawCircleChild.start()
                } else {
                    iMenuListener?.OnMenuCompleteAnimation()
                }
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })

        mAnimatorDrawCircleChild = ValueAnimator.ofFloat(0F, 360F)
        mAnimatorDrawCircleChild.duration = mDuration
        mAnimatorDrawCircleChild.addUpdateListener {
            mSweepAngleChild = it.animatedValue as Float
            invalidate()
        }

        mAnimatorDrawCircleChild.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
//                isDrawMenu = false
                val menu = mListMenu[mPositionCirCleChild - 1]
                menu.isDrawing = false
                menu.isDraw = true

                val angleStart = mAngleCirleMain * mPositionCirCleChild + mAngleDistanceCircleChild

                mPositionCirCleChild += 1

                val angleEnd = /*if (mPositionCirCleChild < mMenuSize) {
                    mAngleCirleMain * mPositionCirCleChild - mAngleDistanceCircleChild
                } else {*/
                    mAngleCirleMain * mPositionCirCleChild
                /*}*/

                mAnimatorDrawCircleMain.setFloatValues(angleStart, angleEnd)
                mAnimatorDrawCircleMain.start()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {
//                val angleStart = mAngleCirleMain * mPositionCirCleChild - mAngleDistanceCircleChild
//                val angleEnd = mAngleCirleMain * mPositionCirCleChild + mAngleDistanceCircleChild
//
//                mAnimatorDrawCircleMain.setFloatValues(angleStart, angleEnd)
//                mAnimatorDrawCircleMain.duration = 1000
//                mAnimatorDrawCircleMain.start()
            }
        })

        mAnimatorText = ValueAnimator.ofInt()
        mAnimatorText.duration = mDuration
        mAnimatorText.repeatCount = ValueAnimator.INFINITE
        mAnimatorText.addUpdateListener { animation ->
            mPositionCharAt = animation.animatedValue as Int
            invalidate()
        }
    }

    fun initIcons(icons: Array<MenuItem>) {
        mMenuItems.addAll(icons)
        mMenuSize = mMenuItems.size + 1
        mAngleCirleMain /= mMenuSize

        val pointIntersection = createMenuItem(mMenuItems[0], mAngleCirleMain - 90)

        mAngleDistanceCircleChild = 180 - findAngle(mCenterCircleMain, pointIntersection)
        mAngleDistanceCircleChild = mAngleCirleMain - mAngleDistanceCircleChild

//        Logger.debug(mAngleDistanceCircleChild)

        mAnimatorDrawCircleMain.setFloatValues(0F, mAngleCirleMain/* - mAngleDistanceCircleChild*/)

        for (i in 2 until mMenuSize) {
            createMenuItem(mMenuItems[i - 1], mAngleCirleMain * i - 90)
        }

        Logger.debug(mListMenu)

        mAnimatorDrawCircleMain.start()
    }

    public override fun onSaveInstanceState(): Parcelable? {
        //begin boilerplate code that allows parent classes to save state
        val superState = super.onSaveInstanceState()
        Logger.debug("onSaveInstanceState")

        val ss = MenuState(superState)
        //end

        ss.mListMenuItem = this.mListMenu
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        //begin boilerplate code so parent classes can restore state
        if (state !is MenuState) {
            super.onRestoreInstanceState(state)
            return
        }

        Logger.debug("onRestoreInstanceState")

        super.onRestoreInstanceState(state.superState)
        //end

        this.mListMenu = state.mListMenuItem!!
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mRadiusCircleMain = h / 2 - mDistanceHalfHeight
        Logger.debug("Rect : mRadiusCircleMain - $mRadiusCircleMain")
        Logger.debug("Rect : 0 - h / 2 + mDistanceHalfHeight - ${0 - h / 2 + mDistanceHalfHeight})")

        mRectOval.set(
            0 - h / 2 + mDistanceHalfHeight, mDistanceHalfHeight
            , mRadiusCircleMain, h - mDistanceHalfHeight
        )

        Logger.debug(mRectOval)
        Logger.debug(mRectOval.centerX(), mRectOval.centerY())
        mCenterCircleMain.set(mRectOval.centerX(), mRectOval.centerY())

        if (mListMenu.size > 0) {
            mSweepAngle = 180F
            mSweepAngleChild = 360F
            iMenuListener?.OnMenuCompleteAnimation()
        }
    }

    private fun createMenuItem(menu: MenuItem, angle: Float): PointF {
//        val bmp = BitmapFactory.decodeResource(resources, mMenuItems[position])
//        val shader = BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        val point = getPosition(
            PointF(0F, height / 2.toFloat()), height / 2 - mDistanceHalfHeight
            , angle
        )

        val rectText = Rect()
        mPaintText.getTextBounds(menu.text, 0, menu.text.length, rectText)

        val startText = if (point.x > width / 2) {
            val endText = point.x - mRadiusCircleChild - mDistanceTextToIcon
            endText - rectText.width()
        } else {
            point.x + mRadiusCircleChild + mDistanceTextToIcon
        }
        Logger.debug(startText)

        val pointIntersection =
            findFirstIntersectionOfTwoCircle(mCenterCircleMain, mRadiusCircleMain, point, mRadiusCircleChild)
        val startAngle = 88 - findAngle(point, pointIntersection)
        Logger.debug(startAngle)
        val rect = RectF(
            point.x - mRadiusCircleChild, point.y - mRadiusCircleChild
            , point.x + mRadiusCircleChild, point.y + mRadiusCircleChild
        )
        mListMenu.add(MenuCircle(point, PointF(startText, point.y + rectText.height() / 2), menu.text, rect, startAngle))
        return pointIntersection
    }

    private fun getPosition(center: PointF, radius: Float, angle: Float): PointF {
        return PointF(
            (center.x + radius * cos(Math.toRadians(angle.toDouble()))).toFloat(),
            (center.y + radius * sin(Math.toRadians(angle.toDouble()))).toFloat()
        )
    }

    private fun findFirstIntersectionOfTwoCircle(
        circleCenter1: PointF,
        radiusCircle1: Float,
        circleCenter2: PointF,
        radiusCircle2: Float
    ): PointF {
        val d = sqrt(
            (circleCenter2.x - circleCenter1.x) * (circleCenter2.x - circleCenter1.x)
                    + (circleCenter2.y - circleCenter1.y) * (circleCenter2.y - circleCenter1.y)
        )

        val a = (radiusCircle1 * radiusCircle1 - radiusCircle2 * radiusCircle2 + d * d) / (2 * d)
        val hi = sqrt(radiusCircle1 * radiusCircle1 - a * a)

        val xIntersection =
            circleCenter2.x + hi * (circleCenter2.y - circleCenter1.y) / d       // also x3=x2-h*(y1-y0)/d
        val yIntersection =
            circleCenter2.y - hi * (circleCenter2.x - circleCenter1.x) / d       // also y3=y2+h*(x1-x0)/d

        return PointF(xIntersection, yIntersection)
    }

    private fun findAngle(circlePoint: PointF, point: PointF): Float {
        val angle =
            Math.toDegrees(atan2((circlePoint.y - point.y).toDouble(), (point.x - circlePoint.x).toDouble())) + 90
        return (if (angle <= 180) angle else angle - 360).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPathCircleMain.reset()
        mPathCircleMain.arcTo(mRectOval, 270f, mSweepAngle)
        canvas?.drawPath(mPathCircleMain, mPaintOval)

        for (menu in mListMenu) {
            if (menu.isDraw) {
                val center = menu.centerPoint
                canvas?.drawCircle(center.x, center.y, mRadiusCircleChild, mPaintPoint)
                canvas?.drawText(menu.mText,menu.startTextPoint.x,menu.startTextPoint.y,mPaintText)
        }
            if (menu.isDrawing) {
//                mPathCircleChild.reset()
//                mPathCircleChild.arcTo(menu.rectF, menu.startAngle, mSweepAngleChild)
//                canvas?.drawPath(mPathCircleChild, mPaintPoint)
                canvas?.drawArc(menu.rectF, menu.startAngle, mSweepAngleChild, true, mPaintPoint)
                canvas?.drawText(menu.mText.substring(0,mPositionCharAt),menu.startTextPoint.x,menu.startTextPoint.y,mPaintText)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            val touchX = event.x
            val touchY = event.y
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    for ((index, menu) in mListMenu.withIndex()) {
                        if (menu.rectF.contains(touchX, touchY)) {
                            iMenuListener?.OnMenuClick(index)
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    Logger.debug("Touching up!")
                }
                MotionEvent.ACTION_MOVE -> {
                    Logger.debug("Sliding your finger around on the screen.")
                }
            }
        }
        return super.onTouchEvent(event)
    }
}