package dev.tran.nam.sortalgorithm.view.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sort.algorithm.databinding.FragmentSortBinding
import dev.tran.nam.sortalgorithm.widget.SortType
import kotlinx.android.synthetic.main.fragment_sort.*
import tran.nam.core.view.BaseFragment

class SortFragment : BaseFragment() {

    private lateinit var mViewDataBinding: FragmentSortBinding

    override fun layoutId(): Int {
        return R.layout.fragment_sort
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.view = this
        arguments?.run {
            val type = when (getInt("type", SortType.SELECTIONSORT.value)) {
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
            mViewDataBinding.type = type
        }
    }

    fun viewImage() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val extras = FragmentNavigatorExtras(
                ivDescription to "imageView"
            )

            findNavController().navigate(R.id.action_sortFragment_to_imageDetailFragment, bundleOf("type" to mViewDataBinding.type), null, extras)
        }else{
            findNavController().navigate(R.id.action_sortFragment_to_imageDetailFragment, bundleOf("type" to mViewDataBinding.type))
        }

    }

    fun example(type: Int) {
        if (mViewDataBinding.type == SortType.INSERTIONSORTI) {
            if (type == 1) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(
                        R.id.action_sortFragment_to_sortExampleFragment,
                        bundleOf("type" to SortType.INSERTIONSORTI)
                    )
            } else {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(
                        R.id.action_sortFragment_to_sortExampleFragment,
                        bundleOf("type" to SortType.INSERTIONSORTII)
                    )
            }
            return
        }
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_sortFragment_to_sortExampleFragment, bundleOf("type" to mViewDataBinding.type))
    }
}