package com.yblxt.sugar.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.yblxt.sugar.common.router.Router
import com.yblxt.sugar.user.R
import com.yblxt.sugar.user.databinding.FragmentUserCenterBinding
import com.yblxt.sugar.user.viewmodel.UserCenterViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author evanyu
 * @date 2021-03-08
 */
@AndroidEntryPoint
class UserCenterFragment : Fragment() {

    private lateinit var binding: FragmentUserCenterBinding
    private val viewModel: UserCenterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_center, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            ARouter.getInstance().build(Router.Path.USER_LOGIN).navigation()
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
        binding.btnMembershipCenter.setOnClickListener {
            ARouter.getInstance().build(Router.Path.USER_MEMBERSHIP_CENTER).navigation()
        }
        binding.btnGlobalDowngradeStrategy.setOnClickListener {
            ARouter.getInstance().build("/user/test").navigation()
        }
    }

}