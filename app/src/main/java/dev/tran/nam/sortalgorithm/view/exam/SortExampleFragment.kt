package dev.tran.nam.sortalgorithm.view.exam

import android.os.Bundle
import android.view.View
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.SortType
import kotlinx.android.synthetic.main.fragment_sort_example.*
import tran.nam.core.view.BaseFragment

class SortExampleFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_sort_example
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            sortExample.setTypeSort(get("type") as SortType)
            sortExample.setListValue(arrayOf(15,8,5,12,56))
        }
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
}