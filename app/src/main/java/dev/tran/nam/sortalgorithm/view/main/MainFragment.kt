package dev.tran.nam.sortalgorithm.view.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.view.MainActivity
import dev.tran.nam.sortalgorithm.widget.IMenuListener
import dev.tran.nam.sortalgorithm.widget.MenuItem
import dev.tran.nam.sortalgorithm.widget.SortType
import kotlinx.android.synthetic.main.fragment_main.*
import tran.nam.Logger
import tran.nam.core.view.BaseFragment

class MainFragment : BaseFragment(), IMenuListener {

    override fun layoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onInitialized() {

        if (requireActivity() is MainActivity){
            (requireActivity() as MainActivity).showAdBanner()
        }

        Logger.debug("onInitialized")
        menuView.initIcons(arrayOf(MenuItem("Selection Sort",R.drawable.icon_select),MenuItem("Insertion Sort",R.drawable.icon_insert)
        ,MenuItem("Bubble Sort",R.drawable.icon_bubble),MenuItem("Quick Sort",R.drawable.icon_quick)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuView.iMenuListener = this
    }

    override fun onResume() {
        Logger.debug("onResume")
        menuView.resumeAnimation()
        super.onResume()
    }

    override fun onPause() {
        Logger.debug("onPause")
        menuView.pauseAnimation()
        super.onPause()
    }

    override fun OnMenuClick(position: Int) {
        val type = when (position) {
            0 -> {
                SortType.SELECTIONSORT.value
            }
            1 -> {
                SortType.INSERTIONSORTI.value
            }
            2 -> {
                SortType.BUBBLESORT.value
            }
            3 -> {
                SortType.QUICKSORT.value
            }
            else -> {
                SortType.SELECTIONSORT.value
            }
        }

        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_mainFragment_to_sortFragment, bundleOf("type" to type))
    }

    override fun OnMenuCompleteAnimation() {

    }

    override fun onDestroyView() {
        Logger.debug("onDestroyView")
        menuView.cancelAnimation()
        super.onDestroyView()
    }
}