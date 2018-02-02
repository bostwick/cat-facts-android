package com.danielbostwick.catfacts.presentation

import android.util.Log
import com.danielbostwick.catfacts.api.CatFactsApi
import com.trello.rxlifecycle.android.FragmentEvent
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CatFactShowPresenter(val view: CatFactShowView,
                           val lifecycle: Observable<FragmentEvent>,
                           val catFactsApi: CatFactsApi,
                           catFactId: Int) {

    private val TAG = this::class.java.simpleName

    init {
        lifecycle.takeUntil { it == FragmentEvent.PAUSE }
                .filter { it == FragmentEvent.RESUME }
                .subscribe { fetchCatFact(catFactId) }
    }

    private fun fetchCatFact(catFactId: Int) {
        Log.d(TAG, "fetchCatFact() - catFactId:$catFactId")

        catFactsApi.getCatfact(catFactId)
                .delay(2, TimeUnit.SECONDS)
                .doOnSubscribe { view.isLoadingVisible = true }
                .doOnUnsubscribe { view.isLoadingVisible = false }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.showCatFact(it) },
                        { Log.e(TAG, Log.getStackTraceString(it)) })
    }
}
