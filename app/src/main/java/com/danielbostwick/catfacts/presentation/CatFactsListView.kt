package com.danielbostwick.catfacts.presentation

import com.danielbostwick.catfacts.api.data.CatFact
import rx.Observable

interface CatFactsListView {
    val catFactClicks: Observable<CatFact>

    fun showCatFacts(items: List<CatFact>)

    var isLoadingVisible: Boolean
}
