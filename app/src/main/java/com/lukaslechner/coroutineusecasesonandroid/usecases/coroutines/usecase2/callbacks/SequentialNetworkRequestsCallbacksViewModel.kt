package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {


    var recentAndroidVersionsCall:Call<List<AndroidVersion>>? = null
    var recentAndroidFeaturesCall:Call<VersionFeatures>? = null
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        recentAndroidVersionsCall = mockApi.getRecentAndroidVersions()
        recentAndroidVersionsCall?.enqueue(object : Callback<List<AndroidVersion>>{
            override fun onResponse(
                p0: Call<List<AndroidVersion>?>,
                response: Response<List<AndroidVersion>?>
            ) {
                if(response.isSuccessful && response.body()?.isNotEmpty() == true){
                    val recentVersions = response.body()?.last()
                    recentAndroidFeaturesCall = mockApi.getAndroidVersionFeatures(recentVersions?.apiLevel ?: 0)
                    recentAndroidFeaturesCall?.enqueue(object : Callback<VersionFeatures>{
                        override fun onResponse(
                            p0: Call<VersionFeatures?>,
                            responseInner: Response<VersionFeatures?>
                        ) {
                            if(responseInner.isSuccessful && responseInner.body() != null){
                                uiState.value = UiState.Success(responseInner.body()!!)
                            }else{
                                uiState.value = UiState.Error("Some Failure happened in get version call")
                            }
                        }

                        override fun onFailure(
                            p0: Call<VersionFeatures?>,
                            p1: Throwable
                        ) {
                            uiState.value = UiState.Error("Some Failure happened in get version call")
                        }

                    })
                }else{
                    uiState.value = UiState.Error("Some Failure happened in get version call")
                }
            }

            override fun onFailure(
                p0: Call<List<AndroidVersion>?>,
                p1: Throwable
            ) {
                uiState.value = UiState.Error("Some Exception happened")
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        recentAndroidVersionsCall?.cancel()
        recentAndroidFeaturesCall?.cancel()
    }
}