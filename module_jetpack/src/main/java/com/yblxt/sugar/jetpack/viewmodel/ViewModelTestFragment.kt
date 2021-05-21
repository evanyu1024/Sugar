package com.yblxt.sugar.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yblxt.sugar.jetpack.R
import kotlinx.android.synthetic.main.fragment_viewmodel_test.*
import java.io.File

/**
 * @author : evanyu
 * @date 2021/02/23
 */
class ViewModelTestFragment : Fragment() {

    // val viewModel: MyViewModel by activityViewModels()
    val viewModel: MyViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewmodel_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_confirm.setOnClickListener {
            viewModel.setData(et_content.text.toString())
        }
        btn_set_file.setOnClickListener {
            viewModel.file = File(requireActivity().cacheDir, "temp_file")
        }
        btn_get_file.setOnClickListener {
            val path = viewModel.file?.absolutePath
            Toast.makeText(requireContext(), "file: $path", Toast.LENGTH_SHORT).show()
        }
    }

}