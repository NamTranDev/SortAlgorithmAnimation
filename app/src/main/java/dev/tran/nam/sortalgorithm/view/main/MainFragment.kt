package dev.tran.nam.sortalgorithm.view.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.IMenuListener
import dev.tran.nam.sortalgorithm.widget.SortType
import kotlinx.android.synthetic.main.fragment_main.*
import tran.nam.core.view.BaseFragment

class MainFragment : BaseFragment(), IMenuListener {

    companion object {
        var isAnimation = false
    }

    override fun layoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onInitialized() {
        initView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAnimation) {
            initView(true)
        }
    }

    fun initView(isComplete: Boolean = false) {
        menuView.initIcons(arrayOf(1, 2, 3, 4), isComplete)
        menuView.iMenuListener = this
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
        isAnimation = true
    }
}