package com.danielbostwick.catfacts.app

import com.danielbostwick.catfacts.api.CatFactsApi

class CatFactsComponent {
    val api: CatFactsApi

    init {
        api = CatFactsApi.create(CatFactsApi.API_ROOT)
    }
}
