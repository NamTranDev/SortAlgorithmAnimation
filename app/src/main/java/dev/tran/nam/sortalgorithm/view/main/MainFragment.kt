package dev.tran.nam.sortalgorithm.view.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.view.sort.SortFragment
import dev.tran.nam.sortalgorithm.widget.SortType
import kotlinx.android.synthetic.main.fragment_main.*
import tran.nam.core.view.BaseFragment

class MainFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onInitialized() {
        super.onInitialized()
        menuView.drawView()
    }
}