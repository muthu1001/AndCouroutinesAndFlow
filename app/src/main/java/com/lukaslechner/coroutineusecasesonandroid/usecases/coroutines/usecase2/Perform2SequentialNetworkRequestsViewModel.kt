package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    var recentAndroidVersionsCall:Call<List<AndroidVersion>>? = null
    var recentAndroidFeaturesCall:Call<VersionFeatures>? = null
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        recentAndroidVersionsCall = mockApi.getRecentAndroidVersionsCall()
        recentAndroidVersionsCall?.enqueue(object : Callback<List<AndroidVersion>>{
            override fun onResponse(
                p0: Call<List<AndroidVersion>?>,
                response: Response<List<AndroidVersion>?>
            ) {
                if(response.isSuccessful && response.body()?.isNotEmpty() == true){
                    val recentVersions = response.body()?.last()
                    recentAndroidFeaturesCall = mockApi.getAndroidVersionFeaturesCall(recentVersions?.apiLevel ?: 0)
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