package com.danielbostwick.catfacts.presentation

import com.danielbostwick.catfacts.api.data.CatFact

interface CatFactsListView {
    fun showCatFacts(items: List<CatFact>)

    fun showCatFact(catFact: CatFact)

    var isLoadingVisible: Boolean
}
