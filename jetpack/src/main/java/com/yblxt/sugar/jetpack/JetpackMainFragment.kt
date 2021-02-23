package com.yblxt.sugar.jetpack

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yblxt.sugar.jetpack.navigation.NavTestActivity
import kotlinx.android.synthetic.main.fragment_jetpack_main.*

/**
 * @author : evanyu
 * @date 2020/11/02
 */
class JetpackMainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_jetpack_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // startActivity
        btn_normal_start_activity.setOnClickListener { startActivity(Intent(context, NavTestActivity::class.java)) }

        // count + 1
        btn_add.setOnClickListener {
            tv_count.text = (tv_count.text.toString().toInt() + 1).toString()
        }

        /*
         * 目的地之间传递参数
         * 方式一：手动创建Bundle对象
         * 方式二：使用 Safe Args 插件
         */
        btn_open_nav_test_activity.setOnClickListener {
            // 传递参数：手动创建 Bundle 对象
            val bundle = Bundle()
            bundle.putString("testArg", "count-${tv_count.text}")
            findNavController().navigate(R.id.action_jetpackMainFragment_to_navTestActivity, bundle)
        }

        btn_open_nav_test_fragment.setOnClickListener {
            // 传递参数：使用 Safe Args 插件
            val action = JetpackMainFragmentDirections
                .actionJetpackMainFragmentToNavTestFragment("count-${tv_count.text}")
            findNavController().navigate(action)
        }

        // open ViewModel test page
        btn_open_viewmodel_test_fragment.setOnClickListener {
            findNavController().navigate(R.id.action_jetpackMainFragment_to_viewModelTestFragment)
        }
    }

}