package com.danielbostwick.catfacts.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.danielbostwick.catfacts.R
import com.danielbostwick.catfacts.api.data.CatFact
import rx.subjects.PublishSubject

class CatFactListItemView @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val TAG = this::class.java.simpleName
    val clicks = PublishSubject.create<CatFactListItemView>()

    val content: TextView
    val catFactId: TextView

    var catFact: CatFact? = null
        set(value) {
            if (value != null) {
                content.text = value.content
                catFactId.text = "Cat Fact #${value.id}"
            }

            field = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_cat_fact_list_item, this, true)

        val padding = resources.getDimensionPixelSize(R.dimen.list_item_padding)

        this.orientation = VERTICAL
        this.setPadding(padding, padding, padding, padding)
        this.isClickable = true

        content = findViewById(R.id.cat_fact_list_item_content)
        catFactId = findViewById(R.id.cat_fact_list_item_id)

        this.setOnClickListener { clicks.onNext(this) }
    }
}
