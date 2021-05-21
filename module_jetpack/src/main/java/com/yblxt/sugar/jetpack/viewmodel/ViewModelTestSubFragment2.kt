package com.yblxt.sugar.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yblxt.sugar.jetpack.R
import kotlinx.android.synthetic.main.fragment_viewmodel_test_sub1.*

/**
 * @author : evanyu
 * @date 2021/02/23
 */
class ViewModelTestSubFragment2 : Fragment() {

    // val viewModel: MyViewModel by activityViewModels()
    val viewModel: MyViewModel by lazy { ViewModelProvider(requireParentFragment()).get(MyViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewmodel_test_sub2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.savedStateLiveData.observe(this, Observer<String> {
            tv_count.text = it
        })
    }

}