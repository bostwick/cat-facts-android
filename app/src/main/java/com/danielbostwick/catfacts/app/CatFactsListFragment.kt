package com.danielbostwick.catfacts.app

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.danielbostwick.catfacts.R
import com.danielbostwick.catfacts.api.data.CatFact
import com.danielbostwick.catfacts.presentation.CatFactsListPresenter
import com.danielbostwick.catfacts.presentation.CatFactsListView
import com.danielbostwick.catfacts.presentation.CatFactsNavigator
import com.danielbostwick.catfacts.ui.CatFactsListAdapter
import rx.Observable

class CatFactsListFragment : BaseFragment(), CatFactsListView {

    override val TAG = CatFactsListFragment::class.java.simpleName

    private val catFactsAdapter = CatFactsListAdapter()

    private lateinit var navigator: CatFactsNavigator
    private lateinit var presenter: CatFactsListPresenter

    private lateinit var catFactsList: RecyclerView
    private lateinit var loadingIndicator: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when {
            context is CatFactsActivity -> {
                this.navigator = context.navigator
            }
            else -> throw IllegalArgumentException("$TAG must attach to a CatFactsActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = CatFactsListPresenter(this, lifecycle(), navigator, CatFactsApp.components.api)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_cat_facts_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catFactsList = view.findViewById<RecyclerView>(R.id.cat_facts_list)
        loadingIndicator = view.findViewById<ProgressBar>(R.id.cat_facts_loading)

        catFactsList.layoutManager = LinearLayoutManager(context)
        catFactsList.adapter = catFactsAdapter
    }

    override fun showCatFacts(items: List<CatFact>) {
        Log.d(TAG, "[${Thread.currentThread().name}] showCatFacts() - items:$items")

        catFactsAdapter.addItems(items)
    }

    override val catFactClicks: Observable<CatFact>
        get() = catFactsAdapter.itemClicks

    override var isLoadingVisible: Boolean
        get() = loadingIndicator.visibility == View.VISIBLE
        set(value) = when (value) {
            true -> loadingIndicator.visibility = View.VISIBLE
            false -> loadingIndicator.visibility = View.GONE
        }
}
