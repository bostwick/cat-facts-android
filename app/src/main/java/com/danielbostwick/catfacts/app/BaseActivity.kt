package com.danielbostwick.catfacts.app

import android.util.Log
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

abstract class BaseActivity : RxAppCompatActivity() {

    val TAG: String = this::class.java.simpleName

    init {
        lifecycle()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "lifecycle event : $it") },
                        { t -> Log.e(TAG, Log.getStackTraceString(t)) })
    }
}
