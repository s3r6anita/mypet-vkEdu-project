package com.f4.mypet.ui.screens.wall

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.VKPETS_ID
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKDefaultValidationHandler
import com.vk.api.sdk.internal.ApiCommand
import com.vk.api.sdk.utils.log.DefaultApiLogger
import com.vk.api.sdk.utils.log.Logger
import com.vk.dto.common.id.UserId
import com.vk.id.AccessToken
import com.vk.id.VKIDUser
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
    @ApplicationContext private val context: Context
) : ViewModel() {

    var token: AccessToken? =
        AccessToken(
            token = "vk1.a.WMwyI6S-BkkNFwJ5bRc_ymj6NKKI" +
                    "-D-ffnWJY8YAUudzSJkyUA1PR8FlVpjUe_SVLgFPD1WBWr04Mu2r5IeCv6A8QU52gou" +
                    "vQVzniiaMh4HSV0GtlfVPK-MRxbPlwwET_B0tUdtl6uU6FE-ZHNAOAli_krS1HQecV44Rluq" +
                    "No9gI60C_NJxKyJv9tD1vzTAHbAcceHDpB7w_HIVjlCtx1w",
            userID = 179272816,
            expireTime = System.currentTimeMillis(),
            userData = VKIDUser(
                firstName = "Софья",
                lastName = "Пономарева"
            )
        )

    private val _vkpetsUIState = MutableStateFlow<GroupsGroupFullDto?>(null)
    val vkpetsUIState = _vkpetsUIState.asStateFlow()

    private val _responseUiState = MutableStateFlow<WallGetResponseDto?>(null)
    val responseUiState = _responseUiState.asStateFlow()

    init {
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
            try {
                saveToken(accessToken)
                return this@withVKIDToken.execute(manager)
            } catch (e: Exception) {
                val res = LinkedBlockingDeque<Result<T>>(1)
                return res.take().getOrThrow()
            }
        }
    }
}
