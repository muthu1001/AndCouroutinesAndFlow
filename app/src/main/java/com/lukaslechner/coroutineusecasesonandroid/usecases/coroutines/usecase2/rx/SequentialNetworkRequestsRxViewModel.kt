package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.rx

import android.annotation.SuppressLint
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    val disposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        mockApi.getRecentAndroidVersions().flatMap { androidVersions ->
            val recentAndroidVersion = androidVersions.last()
            mockApi.getAndroidVersionFeatures(recentAndroidVersion.apiLevel)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {
                    uiState.value = UiState.Success(it)
                },
                onError = {
                    uiState.value = UiState.Error("Some Exception happened")
                }
            ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}