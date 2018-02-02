package com.danielbostwick.catfacts.app

import android.os.Bundle
import android.util.Log
import com.danielbostwick.catfacts.R
import com.danielbostwick.catfacts.presentation.CatFactsPresenter
import com.danielbostwick.catfacts.presentation.CatFactsView

class CatFactsActivity : BaseActivity(), CatFactsView {

    private lateinit var presenter: CatFactsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_facts)

        presenter = CatFactsPresenter(this, lifecycle())
    }

    override fun showCatFactsList() {
        Log.d(TAG, "showCatFactsList()")

        supportFragmentManager.beginTransaction()
                .add(R.id.cat_facts_container, catFactsList)
                .commit()
    }

    override fun showCatFact(catFactId: Int) {
        Log.d(TAG, "showCatFact() - id:$catFactId")

        supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right)
                .add(R.id.cat_facts_container, CatFactShowFragment.newInstance(catFactId))
                .addToBackStack(CatFactShowFragment.TAG)
                .commit()
    }

    private val catFactsList by lazy {
        CatFactsListFragment()
    }
}
