package com.danielbostwick.catfacts.presentation

import android.util.Log
import com.danielbostwick.catfacts.api.CatFactsApi
import com.trello.rxlifecycle.android.FragmentEvent
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CatFactsListPresenter(val view: CatFactsListView,
                            val lifecycle: Observable<FragmentEvent>,
                            val navigator: CatFactsNavigator,
                            val catFactsApi: CatFactsApi) {

    val TAG = CatFactsListPresenter::class.java.simpleName

    init {
        lifecycle.takeUntil { it == FragmentEvent.PAUSE }
                .filter { it == FragmentEvent.RESUME }
                .subscribe { fetchCatFacts() }

        view.catFactClicks
                .doOnNext { Log.d(TAG, "catFactClick - catFact:$it") }
                .subscribe(
                        { navigator.navigateToShowCatFact(it.id) },
                        { Log.e(TAG, Log.getStackTraceString(it)) })
    }

    private fun fetchCatFacts() {
        Log.d(TAG, "fetchCatFacts()")

        catFactsApi.getAllCatfacts()
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.isLoadingVisible = true }
                .doOnUnsubscribe { view.isLoadingVisible = false }
                .subscribe(
                        { view.showCatFacts(it) },
                        { Log.e(TAG, Log.getStackTraceString(it)) })
    }
}
