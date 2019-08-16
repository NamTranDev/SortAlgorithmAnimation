package dev.tran.nam.sortalgorithm.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import dev.tran.nam.sort.algorithm.R
import tran.nam.Logger
import tran.nam.util.DisplayUtil
import java.util.*
import kotlin.math.abs

/**
 * Created by NamTran on 4/6/2017.
 */

class SortView : View {

    private var mListSortValue: ArrayList<SortValue> = ArrayList()
    private var mValues: Array<Int>? = null
    private lateinit var mPaintRectNotSort: Paint
    private lateinit var mPaintRectSorted: Paint
    private lateinit var mPaintStrockSorted: Paint
    private lateinit var mPaintLinePivot: Paint
    private lateinit var mPaintTextNoSort: TextPaint
    private lateinit var mPaintTextSort: TextPaint
    private lateinit var mPaintTextPivot: TextPaint
    private lateinit var animatorTransitionYUpSmall: ValueAnimator
    private lateinit var animatorTransitionYDownSmall: ValueAnimator
    private lateinit var animatorTransitionYDownLarge: ValueAnimator
    private lateinit var animatorTransitionYUpLarge: ValueAnimator
    private lateinit var animatorTransitionXSmall: ValueAnimator
    private lateinit var animatorTransitionXLarge: ValueAnimator
    private lateinit var animationPivotQuickSort: ValueAnimator

    private var begin = 0
    private var insertionsortTemp = 1
    private var bubblesortTemp = 0
    private var temp = 0

    private var isAnimation = false
    private var isStartAnimation = false
    private var insertionWhile = true
    private var insertionSwap = true
    private var bubbleSwap = true

    private var isPartition: Boolean = false
    private var isPartitionAnimation: Boolean = false
    private var isFindPivot: Boolean = false
    private var isPopRange: Boolean = false

    private val mDuration = 500

    private var rectTempSmall: Rect? = null
    private var rectTempLarge: Rect? = null
    private var mDistance: Int = 0
    private var valueTemp = 0
    private var pivotPointView: PivotPointView? = null
    private var quickRange: QuickRange? = null
    private var pivot: SortValue? = null
    private var partition: Int = 0
    private var mLeft: Int = 0
    private var mRight: Int = 0
    private var isRelease = false
    private var isExplain = true
    private var mCurrentPosition = 1
    private var mTempExplain = 0

    //QuickSort
    private var stackQuickRange: Stack<QuickRange>? = null

    private var sortViewListener: SortViewListener? = null

    private var sortType = SortType.SELECTIONSORT

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {

        mPaintRectNotSort = Paint()
        mPaintRectNotSort.strokeWidth = 2.5f
        mPaintRectNotSort.isAntiAlias = true
        mPaintRectNotSort.style = Paint.Style.STROKE
        mPaintRectNotSort.strokeCap = Paint.Cap.ROUND

        mPaintStrockSorted = Paint()
        mPaintStrockSorted.strokeWidth = 2.5f
        mPaintStrockSorted.isAntiAlias = true
        mPaintStrockSorted.style = Paint.Style.STROKE
        mPaintStrockSorted.color = Color.WHITE

        mPaintRectSorted = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintRectSorted.color = ContextCompat.getColor(context, R.color.lico_rice)
        mPaintRectSorted.style = Paint.Style.FILL
        mPaintRectSorted.strokeCap = Paint.Cap.ROUND

        mPaintLinePivot = Paint()
        mPaintLinePivot.color = ContextCompat.getColor(context, R.color.lico_rice)
        mPaintLinePivot.isAntiAlias = true
        mPaintLinePivot.strokeWidth = 5f

        mPaintTextNoSort = TextPaint()
        mPaintTextNoSort.style = Paint.Style.FILL
        mPaintTextNoSort.isAntiAlias = true
        mPaintTextNoSort.color = ContextCompat.getColor(context, R.color.smoky_black)
        mPaintTextNoSort.textSize = 40f * DisplayUtil.scaleDensity(context)
        mPaintTextNoSort.textAlign = Paint.Align.CENTER

        mPaintTextSort = TextPaint()
        mPaintTextSort.style = Paint.Style.FILL_AND_STROKE
        mPaintTextSort.isAntiAlias = true
        mPaintTextSort.color = Color.WHITE
        mPaintTextSort.textSize = 40f * DisplayUtil.scaleDensity(context)
        mPaintTextSort.textAlign = Paint.Align.CENTER

        mPaintTextPivot = TextPaint()
        mPaintTextPivot.style = Paint.Style.FILL_AND_STROKE
        mPaintTextPivot.isAntiAlias = true
        mPaintTextPivot.color = ContextCompat.getColor(context, R.color.smoky_black)
        mPaintTextPivot.textSize = 40f * DisplayUtil.scaleDensity(context)
        mPaintTextPivot.textAlign = Paint.Align.CENTER

        animatorTransitionYUpSmall = ValueAnimator.ofInt(0, 200)
        animatorTransitionYDownSmall = ValueAnimator.ofInt(0, 200)
        animatorTransitionXSmall = ValueAnimator()

        animatorTransitionYDownLarge = ValueAnimator.ofInt(0, 200)
        animatorTransitionYUpLarge = ValueAnimator.ofInt(0, 200)
        animatorTransitionXLarge = ValueAnimator()

        animationDuration(mDuration)

        animatorTransitionYUpSmall.interpolator = LinearInterpolator()
        animatorTransitionYDownSmall.interpolator = LinearInterpolator()
        animatorTransitionXSmall.interpolator = LinearInterpolator()

        animatorTransitionYDownLarge.interpolator = LinearInterpolator()
        animatorTransitionYUpLarge.interpolator = LinearInterpolator()
        animatorTransitionXLarge.interpolator = LinearInterpolator()

        stackQuickRange = Stack()
        pivotPointView = PivotPointView()

        animationPivotQuickSort = ValueAnimator.ofInt(0, 100)
        animationPivotQuickSort.duration = 500

        updateAnimationListener()
        animationListener()

    }

    public override fun onSaveInstanceState(): Parcelable? {
        //begin boilerplate code that allows parent classes to save state
        val superState = super.onSaveInstanceState()

        val ss = SortState(superState)
        //end

        ss.begin = this.begin
        ss.insertionsortTemp = this.insertionsortTemp
        ss.temp = this.temp
        ss.mDistance = this.mDistance
        ss.isAnimation = this.isAnimation
        ss.isStartAnimation = this.isStartAnimation
        ss.insertionWhile = this.insertionWhile
        ss.insertionNotSwap = this.insertionSwap
        ss.sortValues = this.mListSortValue

        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        //begin boilerplate code so parent classes can restore state
        if (state !is SortState) {
            super.onRestoreInstanceState(state)
            return
        }

        Logger.debug("onRestoreInstanceState")

        super.onRestoreInstanceState(state.superState)
        //end

        this.begin = state.begin
        this.insertionsortTemp = state.insertionsortTemp
        this.temp = state.temp
        this.mDistance = state.mDistance
        this.isAnimation = state.isAnimation
        this.isStartAnimation = state.isStartAnimation
        this.insertionWhile = state.insertionWhile
        this.insertionSwap = state.insertionNotSwap
        this.mListSortValue = state.sortValues!!
    }

    private fun noDuration() {
        animatorTransitionYUpSmall.duration = 0
        animatorTransitionYDownSmall.duration = 0
        animatorTransitionYDownLarge.duration = 0
        animatorTransitionYUpLarge.duration = 0
        animatorTransitionXSmall.duration = 0
        animatorTransitionXLarge.duration = 0
    }

    private fun animationDuration(duration: Int) {
        animatorTransitionYUpSmall.duration = duration.toLong()
        animatorTransitionYDownSmall.duration = duration.toLong()
        animatorTransitionYDownLarge.duration = duration.toLong()
        animatorTransitionYUpLarge.duration = duration.toLong()
        animatorTransitionXSmall.duration = duration.toLong()
        animatorTransitionXLarge.duration = duration.toLong()
    }

    fun startAnimation() {
        returnDefault()
        isStartAnimation = true
        updateAnimation(0)
        if (sortViewListener != null)
            sortViewListener!!.startAnimation()
    }

    private fun updateAnimation(durationExplain: Long = 2000L) {
        invalidate()
        if (isExplain) {
            when (sortType) {
                SortType.SELECTIONSORT -> {
//                    Handler().postDelayed({
//                        Logger.debug("Text List: $mListSortValue")
//                        for (j in mCurrentPosition until mListSortValue.size) {
//                            Logger.debug("Text : ${mListSortValue[mTempExplain].value},${mListSortValue[mCurrentPosition].value}")
//
//                            val text = if (mListSortValue[mTempExplain].value > mListSortValue[mCurrentPosition].value)
//                                "Text sort: ${mListSortValue[mTempExplain].value} > ${mListSortValue[mCurrentPosition].value} -> Choose ${mListSortValue[j].value}"
//                            else
//                                "Text sort: ${mListSortValue[mTempExplain].value} < ${mListSortValue[mCurrentPosition].value} -> Choose ${mListSortValue[mTempExplain].value}"
//
//                            sortViewListener?.explainSort(text)
//
//                            if (mListSortValue[mTempExplain].value > mListSortValue[j].value) {
//                                mTempExplain = j
//                            }
//                            break
//                        }
//                        Logger.debug("Text : End")
//
//                        if (mCurrentPosition == mListSortValue.size) {
//                            Logger.debug("Draw Explain : End")
//                            isExplain = false
//                        } else {
//                            Logger.debug("Draw Explain : $mCurrentPosition + $mTempExplain")
//                            val rectCurrent = mListSortValue[mCurrentPosition].rect
//                            val rectChoose = mListSortValue[mTempExplain].rect
//
//                            mListSortValue[mCurrentPosition].isCurrentPosition = true
//                            mListSortValue[mTempExplain].isChooseExplain = true
//
//                            mListSortValue[mCurrentPosition].setPoint(rectCurrent!!.centerX(), rectCurrent.top)
//                            mListSortValue[mTempExplain].setPoint(rectChoose!!.centerX(), rectChoose.top,false)
//                        }
//                        mCurrentPosition++
//                        updateAnimation()
//                    }, durationExplain)
                    isExplain = false
                    updateAnimation()
                }
                SortType.INSERTIONSORTI,
                SortType.INSERTIONSORTII -> {
                    isExplain = false
                    updateAnimation()
                }
                SortType.BUBBLESORT -> {
                    isExplain = false
                    updateAnimation()
                }
                SortType.QUICKSORT -> {
                    isExplain = false
                    updateAnimation()
                }
            }
        } else {
            animationDuration(mDuration)
            when (sortType) {
                SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT -> {
                    animatorTransitionYUpSmall.start()
                    animatorTransitionYDownLarge.start()
                }
                SortType.INSERTIONSORTII -> animatorTransitionYUpSmall.start()
                SortType.QUICKSORT ->
                    // TODO: 4/22/2017
                    if (!isAnimation && !isFindPivot && !isPartition) {
                        animationPivotQuickSort.start()
                    } else {
                        animatorTransitionYUpSmall.start()
                        animatorTransitionYDownLarge.start()
                    }
            }
        }
    }

    private fun returnDefault() {
        if (mValues != null) {
            if (mListSortValue.isEmpty()) {
                setData(width, height)
            } else {
                for (i in mListSortValue.indices) {
                    val sortValue = mListSortValue[i]
                    sortValue.isSort = false
                    sortValue.value = mValues!![i]
                }
            }
        }
        animationDuration(mDuration)
        isStartAnimation = false
        insertionsortTemp = 1
        begin = 0
        when (sortType) {
            SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.INSERTIONSORTII -> temp = 0
            SortType.BUBBLESORT -> temp = mListSortValue.size - 1
            SortType.QUICKSORT -> stackQuickRange!!.push(
                QuickRange(
                    0,
                    mListSortValue.size - 1
                )
            )
        }
        invalidate()
    }

    fun pauseAnimation() {
        if (animatorTransitionYUpSmall.isRunning)
            animatorTransitionYUpSmall.pause()
        if (animatorTransitionYDownSmall.isRunning)
            animatorTransitionYDownSmall.pause()
        if (animatorTransitionXSmall.isRunning)
            animatorTransitionXSmall.pause()
        if (animatorTransitionYDownLarge.isRunning)
            animatorTransitionYDownLarge.pause()
        if (animatorTransitionYUpLarge.isRunning)
            animatorTransitionYUpLarge.pause()
        if (animatorTransitionXLarge.isRunning)
            animatorTransitionXLarge.pause()
    }

    fun resumeAnimation() {
        if (animatorTransitionYUpSmall.isPaused)
            animatorTransitionYUpSmall.resume()
        if (animatorTransitionYDownSmall.isPaused)
            animatorTransitionYDownSmall.resume()
        if (animatorTransitionXSmall.isPaused)
            animatorTransitionXSmall.resume()
        if (animatorTransitionYDownLarge.isPaused)
            animatorTransitionYDownLarge.resume()
        if (animatorTransitionYUpLarge.isPaused)
            animatorTransitionYUpLarge.resume()
        if (animatorTransitionXLarge.isPaused)
            animatorTransitionXLarge.resume()
    }

    fun cancelAnimation() {
        isRelease = true
        Logger.debug("cancelAnimation")
        if (animatorTransitionYUpSmall.isRunning || animatorTransitionYUpSmall.isPaused)
            animatorTransitionYUpSmall.cancel()
        if (animatorTransitionYDownSmall.isRunning || animatorTransitionYDownSmall.isPaused)
            animatorTransitionYDownSmall.cancel()
        if (animatorTransitionXSmall.isRunning || animatorTransitionXSmall.isPaused)
            animatorTransitionXSmall.cancel()
        if (animatorTransitionYDownLarge.isRunning || animatorTransitionYDownLarge.isPaused)
            animatorTransitionYDownLarge.cancel()
        if (animatorTransitionYUpLarge.isRunning || animatorTransitionYUpLarge.isPaused)
            animatorTransitionYUpLarge.cancel()
        if (animatorTransitionXLarge.isRunning || animatorTransitionXLarge.isPaused)
            animatorTransitionXLarge.cancel()
    }

    fun setTypeSort(typeSort: SortType) {
        this.sortType = typeSort
    }

    fun setSortViewListener(sortViewListener: SortViewListener) {
        this.sortViewListener = sortViewListener
    }

    private fun updateAnimationListener() {
        animatorTransitionYUpSmall.addUpdateListener { animation ->
            run {
                if (isRelease)
                    return@run
                val value = animation.animatedValue as Int
                when (sortType) {
                    SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> {
//                        Logger.debug("animatorTransitionYUpSmall value : $value")
                        if (rectTempSmall != null) {
//                            Logger.debug(
//                                "rectTempSmall : " + "\n"
//                                        + "rectTempSmall.top : " + rectTempSmall!!.top + " | "
//                                        + "rectTempSmall.bottom : " + rectTempSmall!!.bottom + " | "
//                                        + "rectTempSmall.left : " + rectTempSmall!!.left + " | "
//                                        + "rectTempSmall.right : " + rectTempSmall!!.right + ") "
//                            )
                            mListSortValue[temp].rect!!.top = rectTempSmall!!.top - value
                            mListSortValue[temp].rect!!.bottom = rectTempSmall!!.bottom - value
//                            Logger.debug(
//                                "mListSortValue.get(temp).getRect : (temp = " + temp + ")" + "\n"
//                                        + "rect.top : " + mListSortValue[temp].rect!!.top + " | "
//                                        + "rect.bottom : " + mListSortValue[temp].rect!!.bottom + " | "
//                                        + "rect.left :" + mListSortValue[temp].rect!!.left + " | "
//                                        + "rect.right : " + mListSortValue[temp].rect!!.right
//                            )

                            invalidate()
                        } /*else {
                            Logger.debug("rectTempSmall null")
                        }*/
                    }
                    SortType.INSERTIONSORTII -> if (rectTempSmall != null) {
                        mListSortValue[insertionsortTemp].rect!!.top = rectTempSmall!!.top - value
                        mListSortValue[insertionsortTemp].rect!!.bottom = rectTempSmall!!.bottom - value
                        invalidate()
                    }/* else {
                        Logger.debug("rectTempSmall null")
                    }*/
                }
            }
        }

        animatorTransitionYDownSmall.addUpdateListener { animation ->
            run {
                if (isRelease)
                    return@run
                val value = animation.animatedValue as Int
                if (rectTempSmall != null) {
                    when (sortType) {
                        SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> {
                            mListSortValue[temp].rect!!.top = rectTempSmall!!.top + value
                            mListSortValue[temp].rect!!.bottom = rectTempSmall!!.bottom + value
                            invalidate()
                        }
                        SortType.INSERTIONSORTII -> {
                            mListSortValue[insertionsortTemp].rect!!.top = rectTempSmall!!.top + value
                            mListSortValue[insertionsortTemp].rect!!.bottom = rectTempSmall!!.bottom + value
                            invalidate()
                        }
                    }
                }
            }
        }

        animatorTransitionXSmall.addUpdateListener { animation ->
            run {
                if (isRelease)
                    return@run
                val value = animation.animatedValue as Int
                if (rectTempSmall != null) {
                    when (sortType) {
                        SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> {
                            mListSortValue[temp].rect!!.left = rectTempSmall!!.left - value
                            mListSortValue[temp].rect!!.right = rectTempSmall!!.right - value
                            invalidate()
                        }
                        SortType.INSERTIONSORTII -> {
                            mListSortValue[insertionsortTemp].rect!!.left = rectTempSmall!!.left - value
                            mListSortValue[insertionsortTemp].rect!!.right = rectTempSmall!!.right - value
                            invalidate()
                        }
                    }
                }
            }
        }

        animatorTransitionYDownLarge.addUpdateListener { animation ->
            run {
                if (isRelease)
                    return@run
                if (rectTempLarge != null) {
                    val value = animation.animatedValue as Int
                    mListSortValue[begin].rect!!.top = rectTempLarge!!.top + value
                    mListSortValue[begin].rect!!.bottom = rectTempLarge!!.bottom + value
                    invalidate()
                }
            }
        }

        animatorTransitionXLarge.addUpdateListener { animation ->
            run {
                if (isRelease)
                    return@run

                val value = animation.animatedValue as Int
                when (sortType) {
                    SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> if (rectTempLarge != null) {
//                        Logger.debug(
//                            "rectTempLarge : " + "\n"
//                                    + "rectTempLarge.top : " + rectTempLarge!!.top + " | "
//                                    + "rectTempLarge.bottom : " + rectTempLarge!!.bottom + " | "
//                                    + "rectTempLarge.left : " + rectTempLarge!!.left + " | "
//                                    + "rectTempLarge.right : " + rectTempLarge!!.right + ") "
//                        )
                        mListSortValue[begin].rect!!.left = rectTempLarge!!.left + value
                        mListSortValue[begin].rect!!.right = rectTempLarge!!.right + value
//                        Logger.debug(
//                            "mListSortValue.get(begin).getRect : (begin = " + begin + ")" + "\n"
//                                    + "rect.top : " + mListSortValue[begin].rect!!.top + " | "
//                                    + "rect.bottom : " + mListSortValue[begin].rect!!.bottom + " | "
//                                    + "rect.left :" + mListSortValue[begin].rect!!.left + " | "
//                                    + "rect.right : " + mListSortValue[begin].rect!!.right
//                        )
                        invalidate()
                    }
                    SortType.INSERTIONSORTII -> if (valueTemp != -1) {
                        for (i in temp until insertionsortTemp) {
                            val rect = mListSortValue[i].rect
                            rect!!.set(
                                rect.left - valueTemp + value,
                                rect.top,
                                rect.right - valueTemp + value,
                                rect.bottom
                            )
                        }
                        valueTemp = value
                        invalidate()
                    }
                }
            }
        }

        animatorTransitionYUpLarge.addUpdateListener { animation ->
            run {
                if (isRelease)
                    return@run

                if (rectTempLarge != null) {
                    val value = animation.animatedValue as Int
                    mListSortValue[begin].rect!!.top = rectTempLarge!!.top - value
                    mListSortValue[begin].rect!!.bottom = rectTempLarge!!.bottom - value
                    invalidate()
                }
            }
        }

        animationPivotQuickSort.addUpdateListener { invalidate() }
    }

    private fun animationListener() {
        animatorTransitionYUpSmall.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                animatorTransitionYDownLarge.start()
            }

            override fun onAnimationEnd(animation: Animator) {
                if (isRelease)
                    return
                when (sortType) {
                    SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> {
                        if (rectTempSmall != null) {
                            rectTempSmall!!.set(mListSortValue[temp].rect!!)
                        }
                        mDistance =
                            abs(mListSortValue[temp].rect!!.centerX() - mListSortValue[begin].rect!!.centerX())
                        Logger.debug("distance : $mDistance")
                        animatorTransitionXSmall.setIntValues(0, mDistance)
                        if (insertionSwap)
                            animatorTransitionXSmall.duration = mDuration.toLong()
                        animatorTransitionXSmall.start()
                    }
                    SortType.INSERTIONSORTII -> {
                        if (rectTempSmall != null) {
                            rectTempSmall!!.set(mListSortValue[insertionsortTemp].rect!!)
                        }
                        mDistance =
                            abs(mListSortValue[insertionsortTemp].rect!!.left - mListSortValue[insertionsortTemp].rect!!.right)
                        animatorTransitionXLarge.setIntValues(0, mDistance)
                        if (valueTemp != -1) {
                            animatorTransitionXLarge.duration = mDuration.toLong()
                        }
                        animatorTransitionXLarge.start()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        animatorTransitionXSmall.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isRelease)
                    return
                when (sortType) {
                    SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> {
                        if (rectTempSmall != null) {
                            rectTempSmall!!.set(mListSortValue[temp].rect!!)
                        }
                        animatorTransitionYDownSmall.start()
                    }
                    SortType.INSERTIONSORTII -> {
                        if (rectTempSmall != null) {
                            rectTempSmall!!.set(mListSortValue[insertionsortTemp].rect!!)
                        }
                        animatorTransitionYDownSmall.start()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        animatorTransitionYDownSmall.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isRelease)
                    return
                when (sortType) {
                    SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> rectTempSmall =
                        null
                    SortType.INSERTIONSORTII -> {
                        rectTempSmall = null
                        isAnimation = false
                        mListSortValue[insertionsortTemp].isSort = true
                        if (valueTemp != -1) {
                            for (i in insertionsortTemp downTo temp + 1) {
                                swap(i, i - 1)
                            }
                        } else {
                            if (!mListSortValue[insertionsortTemp - 1].isSort)
                                mListSortValue[insertionsortTemp - 1].isSort = true
                        }
                        insertionsortTemp++
                        if (insertionsortTemp < mListSortValue.size) {
                            updateAnimation()
                        } else {
                            isStartAnimation = false
                            insertionsortTemp = 1
                            begin = 0
                            temp = 0
                            valueTemp = 0
                            invalidate()
                            if (sortViewListener != null)
                                sortViewListener!!.completeAnimation()
                        }
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        animatorTransitionYDownLarge.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isRelease)
                    return
                if (rectTempLarge != null) {
                    rectTempLarge!!.set(mListSortValue[begin].rect!!)
                }

                animatorTransitionXLarge.setIntValues(0, mDistance)
                if (insertionSwap)
                    animatorTransitionXLarge.duration = mDuration.toLong()
                animatorTransitionXLarge.start()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        animatorTransitionXLarge.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isRelease)
                    return
                when (sortType) {
                    SortType.SELECTIONSORT, SortType.INSERTIONSORTI, SortType.BUBBLESORT, SortType.QUICKSORT -> {
                        if (rectTempLarge != null) {
                            rectTempLarge!!.set(mListSortValue[begin].rect!!)
                        }
                        animatorTransitionYUpLarge.start()
                    }
                    SortType.INSERTIONSORTII -> {
                        mDistance =
                            abs(mListSortValue[insertionsortTemp].rect!!.centerX() - mListSortValue[temp].rect!!.centerX()) +
                                    abs(mListSortValue[temp].rect!!.right - mListSortValue[temp].rect!!.left)
                        animatorTransitionXSmall.setIntValues(0, mDistance)
                        if (valueTemp != -1) {
                            animatorTransitionXSmall.duration = mDuration.toLong()
                        }
                        animatorTransitionXSmall.start()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        animatorTransitionYUpLarge.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isRelease)
                    return
                when (sortType) {
                    SortType.SELECTIONSORT -> {
                        rectTempLarge = null
                        isAnimation = false
                        swap(temp, begin)
                        begin++
                        if (begin < mListSortValue.size) {
                            isExplain = true
                            mCurrentPosition = begin + 1
                            mTempExplain = begin
                            updateAnimation(0)
                        } else {
                            sortViewListener?.explainSort("Complete")
                            isStartAnimation = false
                            begin = 0
                            temp = 0
                            invalidate()
                            if (sortViewListener != null)
                                sortViewListener!!.completeAnimation()
                        }
                    }
                    SortType.INSERTIONSORTI -> {
                        rectTempLarge = null
                        isAnimation = false
                        if (insertionSwap)
                            swap(begin, temp)
                        else
                            mListSortValue[temp].isSort = true
                        temp--
                        if (insertionsortTemp < mListSortValue.size) {
                            updateAnimation()
                        } else {
                            isStartAnimation = false
                            insertionsortTemp = 1
                            begin = 0
                            temp = 0
                            invalidate()
                            if (sortViewListener != null)
                                sortViewListener!!.completeAnimation()
                        }
                    }
                    SortType.BUBBLESORT -> {
                        rectTempLarge = null
                        isAnimation = false
                        if (bubbleSwap)
                            swap(begin, temp)
                        temp--
                        if (bubblesortTemp < mListSortValue.size) {
                            updateAnimation()
                        } else {
                            isStartAnimation = false
                            bubblesortTemp = 0
                            begin = 0
                            temp = mListSortValue.size - 1
                            invalidate()
                            if (sortViewListener != null)
                                sortViewListener!!.completeAnimation()
                        }
                    }
                    SortType.QUICKSORT -> {
                        rectTempLarge = null
                        isAnimation = false
                        if (isPartition)
                            swap(begin, temp)

                        if (!isPartition && !isPartitionAnimation) {
                            if (stackQuickRange!!.isEmpty()) {
                                isStartAnimation = false
                                begin = 0
                                temp = 0
                                if (sortViewListener != null)
                                    sortViewListener!!.completeAnimation()
                                invalidate()
                                return
                            }
                            isFindPivot = false
                            isPopRange = false
                        }
                        updateAnimation()
                    }
                    SortType.INSERTIONSORTII -> {
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        animationPivotQuickSort.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isRelease)
                    return
                isPartition = true
                animatorTransitionYUpSmall.start()
                animatorTransitionYDownLarge.start()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
    }

    fun swap(a: Int, b: Int) {
        Collections.swap(mListSortValue, a, b)
        when (sortType) {
            SortType.SELECTIONSORT -> mListSortValue[b].isSort = true
            SortType.INSERTIONSORTI -> mListSortValue[a].isSort = true
            SortType.INSERTIONSORTII,
            SortType.BUBBLESORT,
            SortType.QUICKSORT -> {
            }
        }
    }

    fun setListValue(values: Array<Int>) {
        Logger.debug("setListValue")
        mValues = values
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setData(w, h)
    }

    private fun setData(w: Int, h: Int) {
        val width = w - (paddingLeft + paddingRight)
        val height = h - (paddingTop + paddingBottom)

        Logger.debug("onSizeChanged")

        if (mValues != null) {
            val rectSize = width / mValues!!.size
            var xLeft = left + paddingLeft
            var xRight = xLeft + rectSize
            val yTop = height / 2 - 50
            val yBottom = height / 2 + 50
            if (mListSortValue.size == 0) {
                for (value in mValues!!) {
                    mListSortValue.add(
                        SortValue(
                            Rect(xLeft, yTop, xRight, yBottom),
                            value
                        )
                    )
                    xLeft = xRight
                    xRight += rectSize
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isStartAnimation) {
            if (isExplain) {
                for (i in mListSortValue.indices) {
                    val sortValue = mListSortValue[i]

                    Logger.debug("Draw Explain Draw : $sortValue (${sortValue.isCurrentPosition} + ${sortValue.isChooseExplain})")

                    if (sortValue.isCurrentPosition && !sortValue.isSort){
                        canvas.drawLine(
                            sortValue.mFirstCurrentLine.x.toFloat(),
                            sortValue.mFirstCurrentLine.y.toFloat(),
                            sortValue.mLastCurrentLine.x.toFloat(),
                            sortValue.mLastCurrentLine.y.toFloat(),
                            mPaintLinePivot
                        )
                        canvas.drawPath(sortValue.mPathCurrent, mPaintLinePivot)
                        sortValue.mPathCurrent.reset()
                        sortValue.isCurrentPosition = false
                    }
                    if (sortValue.isChooseExplain && !sortValue.isSort){
                        canvas.drawLine(
                            sortValue.mFirstChooseLine.x.toFloat(),
                            sortValue.mFirstChooseLine.y.toFloat(),
                            sortValue.mLastChooseLine.x.toFloat(),
                            sortValue.mLastChooseLine.y.toFloat(),
                            mPaintLinePivot
                        )
                        canvas.drawPath(sortValue.mPathChoose, mPaintLinePivot)
                        canvas.drawText(
                            "Choose",
                            sortValue.mPointTextChoose.x.toFloat(),
                            sortValue.mPointTextChoose.y.toFloat(),
                            mPaintTextPivot
                        )
                        sortValue.mPathChoose.reset()
                        sortValue.isChooseExplain = false
                    }
                    draw(sortValue, canvas)
                }
            } else {
                for (i in mListSortValue.indices) {
                    val sortValue = mListSortValue[i]
                    when (sortType) {
                        SortType.SELECTIONSORT -> selectionSort(i, sortValue, canvas)
                        SortType.INSERTIONSORTI -> if (i == 0) {
//                        mListSortValue[0].isSort = true
                            draw(sortValue, canvas)
                        } else {
                            insertionSortI(i, sortValue, canvas)
                        }
                        SortType.INSERTIONSORTII -> if (i == 0) {
//                        mListSortValue[0].isSort = true
                            draw(sortValue, canvas)
                        } else {
                            insertionSortII(i, sortValue, canvas)
                        }
                        SortType.BUBBLESORT -> bubbleSort(i, sortValue, canvas)
                        SortType.QUICKSORT -> if (!isPopRange) {
                            quickRange = stackQuickRange!!.pop()
                            while (!stackQuickRange!!.isEmpty() && isPartition(
                                    quickRange!!.firstPosition,
                                    quickRange!!.lastPosition
                                )
                            ) {
                                quickRange = stackQuickRange!!.pop()
                            }
                            isPopRange = true
                            draw(sortValue, canvas)
                        } else {
                            quickSort(quickRange!!.firstPosition, quickRange!!.lastPosition, sortValue, canvas)
                            draw(sortValue, canvas)
                        }
                    }
                }
            }
        } else {
            for (i in mListSortValue.indices) {
                val sortValue = mListSortValue[i]
                draw(sortValue, canvas)
            }
        }
    }

    private fun draw(sortValue: SortValue, canvas: Canvas) {
        val text = sortValue.value.toString()
        val bound = Rect()
        mPaintTextNoSort.getTextBounds(text, 0, text.length, bound)
        val textHeight = bound.height()
        if (sortValue.isSort) {
            canvas.drawRect(sortValue.rect!!, mPaintRectSorted)
            canvas.drawRect(sortValue.rect!!, mPaintStrockSorted)
        } else
            canvas.drawRect(sortValue.rect!!, mPaintRectNotSort)
        canvas.drawText(
            text,
            sortValue.rect!!.centerX().toFloat(),
            (sortValue.rect!!.centerY() + textHeight / 2).toFloat(),
            if (sortValue.isSort) mPaintTextSort else mPaintTextNoSort
        )
    }

    private fun selectionSort(i: Int, sortValue: SortValue, canvas: Canvas) {
        if (i == begin || i == temp) {
            if (!isAnimation) {
                temp = begin
                for (j in i + 1 until mListSortValue.size) {
                    if (mListSortValue[temp].value > mListSortValue[j].value) {
                        temp = j
                    }
                }
                if (begin == temp) {
                    rectTempSmall = null
                    rectTempLarge = null
                    noDuration()
                } else {
                    rectTempSmall = Rect(mListSortValue[temp].rect)
                    rectTempLarge = Rect(mListSortValue[begin].rect)
                }
                draw(sortValue, canvas)
                isAnimation = true
            } else {
                draw(sortValue, canvas)
            }
        } else {
            draw(sortValue, canvas)
        }
    }


    private fun insertionSortI(i: Int, sortValue: SortValue, canvas: Canvas) {
        if (i == insertionsortTemp) {
            if (!isAnimation) {
                if (insertionWhile) {
                    temp = insertionsortTemp
                }
                while (temp > 0 && mListSortValue[temp].value < mListSortValue[temp - 1].value) {
                    begin = temp - 1
                    rectTempSmall = Rect(mListSortValue[temp].rect)
                    rectTempLarge = Rect(mListSortValue[begin].rect)
                    insertionSwap = true
                    break
                }
                isAnimation = true
                if (temp > 0) {
                    if (rectTempSmall == null && rectTempLarge == null) {
                        insertionSwap = false
                        noDuration()
                    }
                    insertionWhile = false
                } else {
                    isAnimation = false
                    insertionWhile = true
                    insertionsortTemp++
                }
                draw(sortValue, canvas)
            } else {
                draw(sortValue, canvas)
            }
        } else {
            draw(sortValue, canvas)
        }
    }

    private fun insertionSortII(i: Int, sortValue: SortValue, canvas: Canvas) {
        if (i == insertionsortTemp) {
            if (!isAnimation) {
                temp = insertionsortTemp
                while (temp > 0 && mListSortValue[temp].value < mListSortValue[temp - 1].value) {
                    swap(temp, temp - 1)
                    temp -= 1
                }

                if (temp == insertionsortTemp) {
                    rectTempSmall = null
                    valueTemp = -1
                    noDuration()
                } else {
                    for (j in temp until insertionsortTemp) {
                        swap(j, j + 1)
                    }
                    rectTempSmall = Rect(mListSortValue[insertionsortTemp].rect)
                    valueTemp = 0
                }
                isAnimation = true
                draw(sortValue, canvas)
            } else {
                draw(sortValue, canvas)
            }
        } else {
            draw(sortValue, canvas)
        }
    }

    private fun bubbleSort(i: Int, sortValue: SortValue, canvas: Canvas) {
        if (bubblesortTemp == i) {
            if (!isAnimation) {
                for (j in mListSortValue.size - 1 downTo i + 1) {
                    if (j > temp)
                        continue
                    if (mListSortValue[j].value < mListSortValue[j - 1].value) {
                        temp = j
                        begin = j - 1
                        rectTempSmall = Rect(mListSortValue[temp].rect)
                        rectTempLarge = Rect(mListSortValue[begin].rect)
                        bubbleSwap = true
                    }
                    break
                }

                if (rectTempSmall == null && rectTempLarge == null) {
                    bubbleSwap = false
                    noDuration()
                }

                isAnimation = true

                if (temp == i) {
                    temp = mListSortValue.size - 1
                    bubbleSwap = false
                    isAnimation = false
                    mListSortValue[bubblesortTemp].isSort = true
                    bubblesortTemp++
                }
                draw(sortValue, canvas)
            } else {
                draw(sortValue, canvas)
            }
        } else {
            draw(sortValue, canvas)
        }
    }

    private fun quickSort(i: Int, j: Int, sortValue: SortValue, canvas: Canvas) {
        if (!isFindPivot) {
            pivot = findPivot(mListSortValue, i, j)
            isFindPivot = true
            draw(sortValue, canvas)
        } else {
            if (isPartition) {
                if (!isAnimation) {
                    partition(mListSortValue, i, j, pivot)
                    isAnimation = true
                    draw(sortValue, canvas)
                } else {
                    draw(sortValue, canvas)
                }
            } else {
                if (animationPivotQuickSort.isRunning) {
                    canvas.drawText(
                        "Pivot",
                        pivotPointView!!.pointText!!.x.toFloat(),
                        pivotPointView!!.pointText!!.y.toFloat(),
                        mPaintTextPivot
                    )
                    canvas.drawLine(
                        pivotPointView!!.firstLine!!.x.toFloat(),
                        pivotPointView!!.firstLine!!.y.toFloat(),
                        pivotPointView!!.lastLine!!.x.toFloat(),
                        pivotPointView!!.lastLine!!.y.toFloat(),
                        mPaintLinePivot
                    )
                    canvas.drawPath(pivotPointView!!.pathArrow!!, mPaintLinePivot)
                }
                draw(sortValue, canvas)
            }
        }
    }

    private fun findPivot(a: List<SortValue>, i: Int, j: Int): SortValue? {
        var k = i + 1
        val firstKey = a[i].value
        while (k <= j && a[k].value == firstKey) k += 1

        return when {
            k > j -> null
            a[k].value > firstKey -> {
                pivotPointView!!.setPoint(a[k].rect!!.centerX(), a[k].rect!!.bottom + 120)
                a[k]
            }
            else -> {
                pivotPointView!!.setPoint(a[i].rect!!.centerX(), a[i].rect!!.bottom + 120)
                a[i]
            }
        }
    }

    private fun partition(a: List<SortValue>?, i: Int, j: Int, pivot: SortValue?) {
        if (!isPartitionAnimation) {
            begin = i
            temp = j
            isPartitionAnimation = true
        } else {
            temp = mRight
            begin = mLeft
        }
        while (begin < temp) {
            while (a!![begin].value < pivot!!.value) {
                begin += 1
            }

            while (a[temp].value >= pivot.value) {
                temp -= 1
            }

            if (begin < temp) {
                // TODO: 4/21/2017 swap(mLeft,mRight)
                mLeft = begin
                mRight = temp
                rectTempSmall = Rect(mListSortValue[temp].rect)
                rectTempLarge = Rect(mListSortValue[begin].rect)
            }
            break
        }

        if (begin >= temp) {
            partition = begin
            isPartition = false
            isPartitionAnimation = false
            pivotPointView!!.clear()
            noDuration()
            if (!isPartition(i, begin - 1))
                stackQuickRange!!.push(QuickRange(i, begin - 1))
            if (!isPartition(begin, j))
                stackQuickRange!!.push(QuickRange(begin, j))
        }

    }

    private fun isPartition(first: Int, last: Int): Boolean {

        if (first == last) {
            mListSortValue[first].isSort = true
            return true
        }

        for (i in first until last + 1) {
            if (mListSortValue[first].value != mListSortValue[i].value) {
                return false
            }
        }
        for (i in first until last + 1) {
            mListSortValue[i].isSort = true
        }
        return true
    }
}


