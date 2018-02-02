package com.danielbostwick.catfacts.presentation

interface CatFactsView {
    fun showCatFactsList()

    fun showCatFact(catFactId: Int)
}
