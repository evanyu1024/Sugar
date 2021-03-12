package com.yblxt.sugar.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.yblxt.sugar.common.router.Router
import com.yblxt.sugar.user.databinding.ActivityMembershipCenterBinding

/**
 * @author evanyu
 * @date 2021-03-12
 */
@Route(path = Router.Path.USER_MEMBERSHIP_CENTER, extras = Router.Flag.NEED_LOGIN)
class MembershipCenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMembershipCenterBinding.inflate(layoutInflater)
    }

}