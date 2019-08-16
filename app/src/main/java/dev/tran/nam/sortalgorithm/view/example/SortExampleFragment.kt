package dev.tran.nam.sortalgorithm.view.example

import android.os.Bundle
import android.view.View
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.SortType
import dev.tran.nam.sortalgorithm.widget.SortViewListener
import kotlinx.android.synthetic.main.fragment_sort_example.*
import tran.nam.core.view.BaseFragment

class SortExampleFragment : BaseFragment(), SortViewListener {

    override fun layoutId(): Int {
        return R.layout.fragment_sort_example
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            sortExample.setTypeSort(get("type") as SortType)
            val rnds = (8..12).random()
            val arrayNumber = mutableListOf<Int>()
            for (i in 1 until rnds){
                arrayNumber.add((1..50).random())
            }
            sortExample.setListValue(arrayNumber.toTypedArray())
        }
        sortExample.setSortViewListener(this)
    }

    override fun onResume() {
        super.onResume()
        sortExample.resumeAnimation()
    }

    override fun onPause() {
        super.onPause()
        sortExample.pauseAnimation()
    }

    override fun onInitialized() {
        sortExample.startAnimation()
    }

    override fun onDestroyView() {
        sortExample.cancelAnimation()
        super.onDestroyView()
    }

    override fun startAnimation() {

    }

    override fun explainSort(text: String) {
//        tvExplain?.text = text
    }

    override fun completeAnimation() {

    }
}