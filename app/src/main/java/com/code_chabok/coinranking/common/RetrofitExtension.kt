package com.code_chabok.coinranking.common

import retrofit2.Response

suspend fun <RequestType> asApiResponse(apiCall: suspend () -> Response<RequestType>): ApiResponse<RequestType> {
    return try {
        ApiResponse.create(apiCall())
    } catch (t: Throwable) {
        ApiResponse.create(t)
    }
}