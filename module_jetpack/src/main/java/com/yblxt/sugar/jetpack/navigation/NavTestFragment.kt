package com.yblxt.sugar.jetpack.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yblxt.sugar.jetpack.R

/**
 * @author : evanyu
 * @date 2020/11/02
 */
class NavTestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
//            val testArgVal = it.getString("testArg")
            val testArgVal = NavTestFragmentArgs.fromBundle(it).testArg
            Log.d("evanyu", "testArgVal = $testArgVal")
        }
    }

}