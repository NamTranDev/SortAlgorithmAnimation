/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package tran.nam.core.view.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import tran.nam.common.autoCleared
import tran.nam.core.view.BaseFragmentInjection
import tran.nam.core.viewmodel.BaseViewModel
import tran.nam.core.viewmodel.IViewLoading
import java.lang.reflect.ParameterizedType
import javax.inject.Inject


abstract class BaseFragmentVM<V : ViewDataBinding, VM : BaseViewModel> : BaseFragmentInjection(), IViewLoading {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    protected lateinit var mViewModel: VM

    protected lateinit var mViewDataBinding: V

    private var binding by autoCleared<V>()

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val viewModelClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(viewModelClass)
        mViewModel.onAttach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.onCreated()
    }

    override fun onInitialized() {
        super.onInitialized()
        mViewModel.onInitialized()
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        binding = mViewDataBinding
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mViewModel.onSaveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mViewModel.onRestoreState(savedInstanceState)
    }

    override fun onDestroy() {
        this.mViewDataBinding.unbind()
        super.onDestroy()
    }

    override fun showDialogLoading() {
        showLoadingDialog()
    }

    override fun hideDialogLoading() {
        hideLoadingDialog()
    }

    override fun onShowDialogError(message: String?, codeError: Int?) {
        hideDialogLoading()
        showAlert(message, codeError) {
            callbackAlertError(codeError)
        }
    }

    open fun callbackAlertError(codeError: Int?) {}
}
