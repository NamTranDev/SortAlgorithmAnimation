package dev.tran.nam.sortalgorithm.widget

import android.view.View
import androidx.viewpager.widget.ViewPager
import dev.tran.nam.sortalgorithm.view.main.ICarousel

import dev.tran.nam.sortalgorithm.view.main.ICarousel.Companion.MAX_ELEVATION_FACTOR


class ShadowTransformer(private val mViewPager: ViewPager, private val iCarousel: ICarousel) :
    ViewPager.OnPageChangeListener, ViewPager.PageTransformer {
    private var mLastOffset: Float = 0.toFloat()
    private var mScalingEnabled: Boolean = true

    init {
        mViewPager.addOnPageChangeListener(this)
    }

    fun enableScaling(enable: Boolean) {
        if (mScalingEnabled && !enable) {
            // shrink main card
            val currentCard = iCarousel.getCardViewAt(mViewPager.currentItem)
            currentCard.animate().scaleY(1f)
            currentCard.animate().scaleX(1f)
        } else if (!mScalingEnabled && enable) {
            // grow main card
            val currentCard = iCarousel.getCardViewAt(mViewPager.currentItem)
            currentCard.animate().scaleY(1.1f)
            currentCard.animate().scaleX(1.1f)
        }

        mScalingEnabled = enable
    }

    override fun transformPage(page: View, position: Float) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val realCurrentPosition: Int
        val nextPosition: Int
        val baseElevation = iCarousel.baseElevation
        val realOffset: Float
        val goingLeft = mLastOffset > positionOffset

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1
            nextPosition = position
            realOffset = 1 - positionOffset
        } else {
            nextPosition = position + 1
            realCurrentPosition = position
            realOffset = positionOffset
        }

        // Avoid crash on overscroll
        if (nextPosition > iCarousel.getCount() - 1 || realCurrentPosition > iCarousel.getCount() - 1) {
            return
        }

        val currentCard = iCarousel.getCardViewAt(realCurrentPosition)

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (mScalingEnabled) {
            currentCard.scaleX = (1 + 0.1 * (1 - realOffset)).toFloat()
            currentCard.scaleY = (1 + 0.1 * (1 - realOffset)).toFloat()
        }
        currentCard.cardElevation = baseElevation + (baseElevation
                * (MAX_ELEVATION_FACTOR - 1).toFloat() * (1 - realOffset))

        val nextCard = iCarousel.getCardViewAt(nextPosition)

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (mScalingEnabled) {
            nextCard.scaleX = (1 + 0.1 * realOffset).toFloat()
            nextCard.scaleY = (1 + 0.1 * realOffset).toFloat()
        }
        nextCard.cardElevation = baseElevation + (baseElevation
                * (MAX_ELEVATION_FACTOR - 1).toFloat() * realOffset)

        mLastOffset = positionOffset
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
