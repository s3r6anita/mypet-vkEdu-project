package com.f4.mypet.data.network

import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail

private val vkAuthCallback = object : VKID.AuthCallback {
    override fun onSuccess(accessToken: AccessToken) {
        val token = accessToken.token
        //...
    }

    override fun onFail(fail: VKIDAuthFail) {
        when (fail) {
            is VKIDAuthFail.Canceled -> { /*...*/ }
            else -> {
                //...
            }
        }
    }
}