package dev.tran.nam.sortalgorithm.view.main

import androidx.cardview.widget.CardView

interface ICarousel {

    val baseElevation: Float

    fun getCount(): Int

    fun getCardViewAt(position: Int): CardView

    companion object {
        const val MAX_ELEVATION_FACTOR = 8
    }
}
