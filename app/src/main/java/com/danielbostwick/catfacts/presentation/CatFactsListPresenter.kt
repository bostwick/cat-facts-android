package com.danielbostwick.catfacts.presentation

import android.util.Log
import com.danielbostwick.catfacts.api.CatFactsApi
import com.danielbostwick.catfacts.api.data.CatFact
import com.trello.rxlifecycle.android.FragmentEvent
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CatFactsListPresenter(val view: CatFactsListView,
                            val lifecycle: Observable<FragmentEvent>,
                            val catFactsApi: CatFactsApi) {

    val TAG = CatFactsListPresenter::class.java.simpleName

    init {
        lifecycle.takeUntil { it == FragmentEvent.PAUSE }
                .filter { it == FragmentEvent.RESUME }
                .subscribe { fetchCatFacts() }
    }

    private fun fetchCatFacts() {
        Log.d(TAG, "fetchCatFacts()")

        catFactsApi.getAllCatfacts()
                .delay(2, TimeUnit.SECONDS)
                .doOnSubscribe { view.isLoadingVisible = true }
                .doOnUnsubscribe { view.isLoadingVisible = false }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.showCatFacts(it) },
                        { Log.e(TAG, Log.getStackTraceString(it)) })
    }

    fun onCatFactClicked(catFact: CatFact) {
        Log.d(TAG, "onCatFactClicked() - catFact:$catFact")

        view.showCatFact(catFact)
    }
}
