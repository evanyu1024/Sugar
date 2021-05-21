package com.yblxt.sugar.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yblxt.sugar.jetpack.R
import kotlinx.android.synthetic.main.fragment_viewmodel_test_sub1.*
import timber.log.Timber

/**
 * @author : evanyu
 * @date 2021/02/23
 */
class ViewModelTestSubFragment1 : Fragment() {

    // val viewModel: MyViewModel by activityViewModels()
    val viewModel: MyViewModel by lazy { ViewModelProvider(requireParentFragment()).get(MyViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        return inflater.inflate(R.layout.fragment_viewmodel_test_sub1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated -> savedInstanceState = $savedInstanceState")
        viewModel.savedStateLiveData.observe(this, Observer<String> {
            tv_count.text = it
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState -> outState = $outState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.d("onViewCreated -> onViewStateRestored = $savedInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

}