package dev.tran.nam.sortalgorithm.view.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.view.sort.SortFragment
import dev.tran.nam.sortalgorithm.widget.ShadowTransformer
import dev.tran.nam.sortalgorithm.widget.SortType
import tran.nam.core.view.BaseFragment

class MainFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vpSort = view.findViewById<ViewPager>(R.id.vpSort)

        val selectionSort = SortFragment()
        var bundle = Bundle()
        bundle.putInt("SortType",SortType.SELECTIONSORT.value)
        selectionSort.arguments = bundle

        val insertionSort = SortFragment()
        bundle = Bundle()
        bundle.putInt("SortType",SortType.INSERTIONSORTI.value)
        insertionSort.arguments = bundle

        val bubbleSort = SortFragment()
        bundle = Bundle()
        bundle.putInt("SortType",SortType.BUBBLESORT.value)
        bubbleSort.arguments = bundle

        val quickSort = SortFragment()
        bundle = Bundle()
        bundle.putInt("SortType",SortType.QUICKSORT.value)
        quickSort.arguments = bundle

        val listSortFragment = ArrayList<SortFragment>()
        listSortFragment.add(selectionSort)
        listSortFragment.add(insertionSort)
        listSortFragment.add(bubbleSort)
        listSortFragment.add(quickSort)

        val adapter = SortFragmentPagerAdapter(
            requireActivity().supportFragmentManager, dpToPixels(2, requireContext()),
            listSortFragment
        )
        val shadowTransformer = ShadowTransformer(vpSort, adapter)

        vpSort.adapter = adapter
        vpSort.setPageTransformer(false, shadowTransformer)
        vpSort.offscreenPageLimit = 3
    }

    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }
}