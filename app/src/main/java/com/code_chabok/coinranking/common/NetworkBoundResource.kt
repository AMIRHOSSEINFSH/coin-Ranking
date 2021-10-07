package com.code_chabok.coinranking.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.code_chabok.coinranking.data.model.dataClass.ServerModel.CoinListResource.CoinListResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

abstract class NetworkBoundResource<ResultType, RequestType> : CoroutineScope {

    private val result = MediatorLiveData<Resource<ResultType>>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    init {
        fetchFromNetwork()
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {

        val dbSource = loadFromDb()

        val apiResponse = liveData(coroutineContext) {
            emit(asApiResponse { createCall() })
        }

        // we attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.Loading(newData))
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response) {
                is ApiSuccessResponse -> {
                    launch {
                        saveCallResult(response.body)

                        withContext(Dispatchers.Main) {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.Success(newData))
                            }
                        }
                    }
                }

                is ApiEmptyResponse -> {
                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.Success(newData))
                    }
                }

                is ApiErrorResponse -> {
                    onFetchFailed()

                    result.addSource(dbSource) { newData ->
                        setValue(Resource.Error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected abstract suspend fun saveCallResult(response: RequestType)

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract suspend fun createCall(): Response<RequestType>
}