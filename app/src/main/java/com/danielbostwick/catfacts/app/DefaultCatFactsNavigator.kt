package com.danielbostwick.catfacts.app

import android.util.Log
import com.danielbostwick.catfacts.R
import com.danielbostwick.catfacts.presentation.CatFactsNavigator

class DefaultCatFactsNavigator(val parent: CatFactsActivity) : CatFactsNavigator {
    override val TAG = this::class.java.simpleName.substring(0, 23)

    override fun navigateToCatFactsList() {
        Log.d(TAG, "navigateToCatFactsList()")

        parent.supportFragmentManager.beginTransaction()
                .add(R.id.cat_facts_container, catFactsList)
                .commit()
    }

    override fun navigateToShowCatFact(catFactId: Int) {
        Log.d(TAG, "navigateToShowCatFact() - id:$catFactId")

        parent.supportFragmentManager.beginTransaction()
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