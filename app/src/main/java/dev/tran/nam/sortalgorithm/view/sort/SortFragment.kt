package dev.tran.nam.sortalgorithm.view.sort

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.SortType
import tran.nam.Logger
import tran.nam.core.view.BaseFragment

class SortFragment : BaseFragment() {

    lateinit var mCardView : CardView

    override fun layoutId(): Int {
        return R.layout.fragment_sort
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCardView = view.findViewById(R.id.cardContain)
        val tvSortType = view.findViewById<TextView>(R.id.tvSortType)
        arguments?.run {
            val type = this.getInt("SortType")
            Logger.debug(type)
            when(type){
                SortType.SELECTIONSORT.value -> {
                    tvSortType.text = "SELECTIONSORT"
                }
                SortType.INSERTIONSORTI.value -> {
                    tvSortType.text = "INSERTIONSORTI"
                }
                SortType.BUBBLESORT.value -> {
                    tvSortType.text = "BUBBLESORT"
                }
                SortType.QUICKSORT.value -> {
                    tvSortType.text = "QUICKSORT"
                }
            }
        }
    }
}