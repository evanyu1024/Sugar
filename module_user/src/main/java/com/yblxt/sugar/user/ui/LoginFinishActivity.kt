package com.yblxt.sugar.user.ui

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yblxt.sugar.common.base.BaseViewBindingActivity
import com.yblxt.sugar.common.router.Router
import com.yblxt.sugar.user.databinding.ActivityLoginFinishBinding

/**
 * @author evanyu
 * @date 2021/02/26
 */
@Route(path = Router.Path.USER_LOGIN_FINISH)
class LoginFinishActivity : BaseViewBindingActivity<ActivityLoginFinishBinding>() {

    @Autowired
    @JvmField
    open var userName: String? = null

    @Autowired
    @JvmField
    open var userPwd: String? = null

    override fun init() {
        ARouter.getInstance().inject(this)
        binding.tvUserInfo.text = "$userName/$userPwd"
    }

}