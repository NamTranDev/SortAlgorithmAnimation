package dev.tran.nam.sortalgorithm.view.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sort.algorithm.databinding.FragmentSortBinding
import dev.tran.nam.sort.algorithm.databinding.FragmentSortExampleBinding
import dev.tran.nam.sortalgorithm.widget.SortType
import dev.tran.nam.sortalgorithm.widget.SortViewListener
import kotlinx.android.synthetic.main.fragment_sort_example.*
import tran.nam.Logger
import tran.nam.core.view.BaseFragment

class SortExampleFragment : BaseFragment(), SortViewListener {

    private lateinit var mViewDataBinding: FragmentSortExampleBinding
    var isCompleteAnimation = ObservableField<Boolean>()

    override fun layoutId(): Int {
        return R.layout.fragment_sort_example
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.view = this
        sortExample.setSortViewListener(this)
        arguments?.run {
            sortExample.setTypeSort(get("type") as SortType)
            val rnds = (8..12).random()
            val arrayNumber = mutableListOf<Int>()
            for (i in 1 until rnds){
                arrayNumber.add((1..50).random())
            }
            sortExample.setListValue(arrayNumber.toTypedArray())
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

    override fun startAnimation() {
        Logger.debug("startAnimation")
        isCompleteAnimation.set(false)
    }

    fun rework(){
        sortExample.startAnimation()
    }

    override fun explainSort(text: String) {

    }

    override fun completeAnimation() {
        Logger.debug("completeAnimation")
        isCompleteAnimation.set(true)
    }
}