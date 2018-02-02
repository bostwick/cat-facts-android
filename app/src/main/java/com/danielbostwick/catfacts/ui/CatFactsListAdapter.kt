package com.danielbostwick.catfacts.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.danielbostwick.catfacts.api.data.CatFact
import com.danielbostwick.catfacts.ui.CatFactsListAdapter.CatFactListItemVH
import rx.subjects.PublishSubject

class CatFactsListAdapter : RecyclerView.Adapter<CatFactListItemVH>() {
    private val TAG = this::class.java.simpleName
    private val items: ArrayList<CatFact> = ArrayList()

    val itemClicks = PublishSubject.create<CatFact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactListItemVH {
        val view = CatFactListItemView(parent.context)
        view.clicks.subscribe { itemClicks.onNext(it.catFact) }

        return CatFactListItemVH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CatFactListItemVH, position: Int) {
        holder.listItem.catFact = this.items[position]
    }

    fun addItems(items: List<CatFact>) {
        Log.d(TAG, "addItems() - items:$items")

        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class CatFactListItemVH(itemView: CatFactListItemView) : RecyclerView.ViewHolder(itemView) {
        val listItem: CatFactListItemView
            get() = super.itemView as CatFactListItemView
    }
}
