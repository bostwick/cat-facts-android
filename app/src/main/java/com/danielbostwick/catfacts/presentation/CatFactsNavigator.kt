package com.danielbostwick.catfacts.presentation

interface CatFactsNavigator {
    val TAG: String

    fun navigateToCatFactsList()

    fun navigateToShowCatFact(catFactId: Int)
}
