package com.danielbostwick.catfacts.app

import android.os.Bundle
import com.danielbostwick.catfacts.R
import com.danielbostwick.catfacts.presentation.CatFactsNavigator
import com.danielbostwick.catfacts.presentation.CatFactsPresenter

class CatFactsActivity : BaseActivity() {

    internal lateinit var navigator: CatFactsNavigator
    private lateinit var presenter: CatFactsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_facts)

        navigator = DefaultCatFactsNavigator(this)
        presenter = CatFactsPresenter(navigator, lifecycle())
    }
}
