package com.yblxt.sugar.common.router

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author evanyu
 * @date 2021-03-12
 */
@Route(path = "/degrade/global/service")
class DegradeServiceImpl: DegradeService {

    override fun init(context: Context?) {
        // empty
    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        ARouter.getInstance().build(Router.Path.PAGE_LOST).greenChannel().navigation()
    }

}