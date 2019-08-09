package dev.tran.nam.sortalgorithm.view.sort

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.SortType
import kotlinx.android.synthetic.main.fragment_sort.*
import tran.nam.core.view.BaseFragment

class SortFragment : BaseFragment() {

    lateinit var mCardView : CardView

    override fun layoutId(): Int {
        return R.layout.fragment_sort
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val type = when(getInt("type",SortType.SELECTIONSORT.value)){
                SortType.SELECTIONSORT.value -> {
                    SortType.SELECTIONSORT
                }
                SortType.INSERTIONSORTI.value -> {
                    SortType.INSERTIONSORTI
                }
                SortType.BUBBLESORT.value -> {
                    SortType.BUBBLESORT
                }
                SortType.QUICKSORT.value -> {
                    SortType.QUICKSORT
                }
                else -> {
                    SortType.SELECTIONSORT
                }
            }
            sortExample.setTypeSort(type)

        }
        sortExample.setListValue(arrayOf(15,8,5,12,56))
        sortExample.startAnimation()
    }
}