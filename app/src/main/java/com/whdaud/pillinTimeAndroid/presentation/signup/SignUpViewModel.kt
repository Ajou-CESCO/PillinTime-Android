package com.whdaud.pillinTimeAndroid.presentation.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.whdaud.pillinTimeAndroid.data.local.LocalUserDataSource
import com.whdaud.pillinTimeAndroid.data.remote.dto.FCMTokenDTO
import com.whdaud.pillinTimeAndroid.data.remote.dto.request.SignUpRequest
import com.whdaud.pillinTimeAndroid.data.remote.dto.response.RelationReqResponse
import com.whdaud.pillinTimeAndroid.domain.repository.FcmRepository
import com.whdaud.pillinTimeAndroid.domain.repository.RelationRepository
import com.whdaud.pillinTimeAndroid.domain.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val relationRepository: RelationRepository,
    private val localUserDataSource: LocalUserDataSource,
    private val fcmRepository: FcmRepository
) : ViewModel() {
    private val _userName = MutableStateFlow("")
    private val _userPhone = MutableStateFlow("")
    private val _userSsn = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    private val _managerRequest = MutableStateFlow<List<RelationReqResponse>>(emptyList())

    val userName: StateFlow<String> = _userName.asStateFlow()
    var isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val managerRequest: StateFlow<List<RelationReqResponse>> = _managerRequest.asStateFlow()


    init {
        viewModelScope.launch {
            _userName.value = localUserDataSource.getUserName().firstOrNull().orEmpty()
        }
    }

    fun signUp(navController: NavController, isManager: Boolean) {
        viewModelScope.launch {
            val name = localUserDataSource.getUserName().firstOrNull().orEmpty()
            val phone = localUserDataSource.getUserPhone().firstOrNull().orEmpty()
            val ssn = localUserDataSource.getUserSsn().firstOrNull().orEmpty()
            _userName.value = name
            _userPhone.value = phone
            _userSsn.value = ssn

            val userSignUpInfo = SignUpRequest(
                ssn = _userSsn.value,
                name = _userName.value,
                phone = _userPhone.value,
                isManager  = isManager
                )
            Log.d("signup user info", "name: ${userSignUpInfo.name} / ssn: ${userSignUpInfo.ssn} / phone: ${userSignUpInfo.phone} / isManager: ${userSignUpInfo.isManager}")
            val response = signUpRepository.signUp(userSignUpInfo)
            response.onSuccess { authenticateResponse ->
                Log.e("SignUp", "Succeed to sign up: ${authenticateResponse.status}")
                localUserDataSource.saveAccessToken(authenticateResponse.result.accessToken)
                postFcmToken()
                val destination = if (isManager) "bottomNavigatorScreen" else "signupClientScreen"
                _isLoading.value = true
                delay(3000)
                navController.navigate(destination)
            }.onFailure {authenticateResponse ->
                Log.e("SignUp", "Failed to sign up: ${authenticateResponse.cause} ${authenticateResponse.localizedMessage}")
            }
        }
    }
    fun getManagerRequest() {
        viewModelScope.launch {
            val response = relationRepository.getRelationRequest()
            response.onSuccess { requestResponse ->
                _managerRequest.value = requestResponse.result
            }.onFailure { requestResponse ->
                Log.e("fetch Manager Request", "Failed to fetch requests: ${requestResponse.message}")
            }
        }
    }
    fun acceptManagerRequest(requestId: Int, managerName: String, navController: NavController, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val response = relationRepository.postRelation(requestId)
            response.onSuccess {
                if(it.status == 200) {
                    onResult("${managerName}님과 보호관계를 맺었습니다")
                    navController.navigate("bottomNavigatorScreen") {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                } else {
                    onResult("네트워크 에러 발생 다시 시도해주세요")
                }
                Log.e("post relation", "succeed to make relation: ${it.message}")
            }.onFailure {
                if(it.message?.contains("403") == true) {
                    onResult("${managerName}님이 프리미엄 회원이 아닙니다")
                } else {
                    onResult("알 수 없는 오류 발생 다시 시도해주세요")
                }
                Log.e("post relation", "Failed to make relation: ${it.message}")
            }
        }
    }
    private fun postFcmToken() {
        viewModelScope.launch {
            val token = Firebase.messaging.token.await()
            Log.e("FCM token:", token)
            val result = fcmRepository.postFcmToken(FCMTokenDTO(token))
            result.onSuccess {
                Log.e("SignInViewModel FCM Token", "succeeded to post FCM Token: ${it.status}")
            }.onFailure {
                Log.e("SignInViewModel FCM Token", "failed to post FCM Token: ${it.cause}")
            }
        }
    }
}