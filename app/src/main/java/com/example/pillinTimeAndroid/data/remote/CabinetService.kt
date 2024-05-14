package com.example.pillinTimeAndroid.data.remote

import com.example.pillinTimeAndroid.data.remote.dto.request.CabinetRequest
import com.example.pillinTimeAndroid.data.remote.dto.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.Objects

interface CabinetService {
    @POST("/api/cabinet")
    suspend fun postRegisterCabinet(
        @Header("Authorization") accessToken: String,
        @Body cabinetRequest: CabinetRequest
    ): BaseResponse<Objects>
}