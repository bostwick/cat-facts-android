package com.danielbostwick.catfacts.presentation

import com.trello.rxlifecycle.android.ActivityEvent
import rx.Observable

class CatFactsPresenter(val navigator: CatFactsNavigator,
                        lifecycle: Observable<ActivityEvent>) {
    init {
        lifecycle
                .takeUntil { it == ActivityEvent.DESTROY }
                .filter { it == ActivityEvent.RESUME }
                .subscribe { navigator.navigateToCatFactsList() }
    }
}
