package com.example.pillinTimeAndroid.presentation.mypage.editinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillinTimeAndroid.data.local.LocalUserDataSource
import com.example.pillinTimeAndroid.data.remote.dto.UserDTO
import com.example.pillinTimeAndroid.data.remote.dto.request.SignInRequest
import com.example.pillinTimeAndroid.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localUserDataSource: LocalUserDataSource,
    ) : ViewModel() {
    private val _userDetails = MutableStateFlow<UserDTO?>(null)
    val userDetails = _userDetails.asStateFlow()

    init {
        getUserInfo()
    }
    private fun getUserInfo() {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            val result = userRepository.getUserInfo("Bearer $accessToken")
            result.onSuccess {
                _userDetails.value = it.result
                Log.d("EditInfoViewModel", "Succeeded to fetch: ${it.result}")
            }.onFailure {
                Log.e("EditInfoViewModel", "Failed to fetch user details: ${it.message}")
            }
        }
    }

    fun patchUserInfo(updatedUser: SignInRequest) {
        viewModelScope.launch {
            val accessToken = localUserDataSource.getAccessToken().firstOrNull().orEmpty()
            val result = userRepository.patchUserInfo("Bearer $accessToken", updatedUser)
            result.onSuccess {
                Log.d("EditInfoViewModel", "Succeeded to patch: ${it.result}")
            }.onFailure {
                Log.e("EditInfoViewModel", "Failed to patch: ${it.message}")
            }
        }
    }
}