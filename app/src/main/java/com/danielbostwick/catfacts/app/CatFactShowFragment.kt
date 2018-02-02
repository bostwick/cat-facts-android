package com.danielbostwick.catfacts.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.danielbostwick.catfacts.R
import com.danielbostwick.catfacts.api.data.CatFact
import com.danielbostwick.catfacts.presentation.CatFactShowPresenter
import com.danielbostwick.catfacts.presentation.CatFactShowView
import com.danielbostwick.catfacts.ui.GifImageView

class CatFactShowFragment : BaseFragment(), CatFactShowView {

    companion object {
        val TAG = CatFactShowFragment::class.java.simpleName

        private const val ARG_CAT_FACT_ID = "argCatFactId"

        fun newInstance(catFactId: Int) = CatFactShowFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_CAT_FACT_ID, catFactId)
            }
        }
    }

    override val TAG = CatFactShowFragment.TAG

    private val argCatFactId: Int
        get() = arguments!!.getInt(ARG_CAT_FACT_ID)

    private lateinit var loadingIndicator: ProgressBar
    private lateinit var catFactContent: TextView
    private lateinit var catFactId: TextView
    private lateinit var nyanCat: GifImageView

    private lateinit var presenter: CatFactShowPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = CatFactShowPresenter(this, lifecycle(), CatFactsApp.components.api, argCatFactId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_cat_fact_show, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingIndicator = view.findViewById(R.id.cat_fact_loading)
        catFactContent = view.findViewById(R.id.cat_fact_content)
        catFactId = view.findViewById(R.id.cat_fact_id)
        nyanCat = view.findViewById(R.id.cat_fact_loading_gif)

        nyanCat.setGifImageResource(R.raw.nyan_cat)
    }

    override fun showCatFact(catFact: CatFact) {
        catFactId.text = "Cat Fact #${catFact.id}"
        catFactContent.text = catFact.content
    }

    override var isLoadingVisible: Boolean
        get() = loadingIndicator.visibility == View.VISIBLE
        set(value) = when (value) {
            true -> nyanCat.visibility = View.VISIBLE // loadingIndicator.visibility = View.VISIBLE
            false -> nyanCat.visibility = View.GONE // loadingIndicator.visibility = View.GONE
        }
}
