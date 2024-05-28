package com.f4.mypet.ui.screens.wall

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.VKPETS_ID
import com.f4.mypet.data.network.NetworkRepository
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKDefaultValidationHandler
import com.vk.api.sdk.internal.ApiCommand
import com.vk.api.sdk.utils.log.DefaultApiLogger
import com.vk.api.sdk.utils.log.Logger
import com.vk.dto.common.id.UserId
import com.vk.id.AccessToken
import com.vk.sdk.api.groups.GroupsService
import com.vk.sdk.api.groups.dto.GroupsGroupFullDto
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingDeque
import javax.inject.Inject

@SuppressWarnings("TooGenericExceptionCaught")
@HiltViewModel
class PetsWallViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    var token: AccessToken? = null

    private val _vkpetsUIState = MutableStateFlow<GroupsGroupFullDto?>(null)
    val vkpetsUIState = _vkpetsUIState.asStateFlow()

    private val _responseUiState = MutableStateFlow<WallGetResponseDto?>(null)
    val responseUiState = _responseUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            token = networkRepository.getVKtoken()
            VK.setConfig(
                VKApiConfig(
                    context = context,
                    appId = 0,
                    validationHandler = VKDefaultValidationHandler(context),
                    apiHostProvider = { "api.vk.com" },
                    logger = DefaultApiLogger(lazy { Logger.LogLevel.VERBOSE }, "API")
                )
            )
            getGroup()
        }
    }

    private fun getGroup() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (token != null) {
                    _vkpetsUIState.value =
                        VK.executeSync(
                            GroupsService()
                                .groupsGetById(groupIds = listOf(UserId(VKPETS_ID)))
                                .withVKIDToken(token!!)
                        ).first()
                    Log.d("token", token.toString())
                }
            } catch (ex: Exception) {
                null
            }

        }
    }

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (token != null) {
                    _responseUiState.value =
                        VK.executeSync(
                            WallService()
                                .wallGet(ownerId = UserId(-VKPETS_ID), count = 20)
                                .withVKIDToken(token!!)

                        )
                }
            } catch (ex: Exception) {
                null
            }

        }
    }
}

@SuppressWarnings("TooGenericExceptionCaught")
fun <T> ApiCommand<T>.withVKIDToken(
    accessToken: AccessToken

): ApiCommand<T> {
    return object : ApiCommand<T>() {

        private fun saveToken(token: AccessToken) {
            VK.saveAccessToken(
                userId = UserId(token.userID),
                accessToken = token.token,
                secret = null,
                expiresInSec = ((System.currentTimeMillis() - token.expireTime) / 1000).toInt(),
                createdMs = System.currentTimeMillis()
            )
        }

        override fun onExecute(manager: VKApiManager): T {
            return try {
                saveToken(accessToken)
                this@withVKIDToken.execute(manager)
            } catch (e: Exception) {
                val res = LinkedBlockingDeque<Result<T>>(1)
                res.take().getOrThrow()
            }
        }
    }
}
