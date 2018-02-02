package com.danielbostwick.catfacts.presentation

import com.danielbostwick.catfacts.api.data.CatFact

interface CatFactShowView {
    fun showCatFact(catFact: CatFact)

    var isLoadingVisible: Boolean
}