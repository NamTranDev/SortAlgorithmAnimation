package dev.tran.nam.sortalgorithm.view.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sort.algorithm.databinding.FragmentImageViewBinding
import dev.tran.nam.sortalgorithm.widget.SortType
import tran.nam.core.view.BaseFragment

class ImageViewFragment : BaseFragment() {

    private lateinit var mViewDataBinding: FragmentImageViewBinding

    override fun layoutId(): Int {
        return R.layout.fragment_image_view
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return mViewDataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run {
            mViewDataBinding.type = get("type") as SortType
        }
    }

}