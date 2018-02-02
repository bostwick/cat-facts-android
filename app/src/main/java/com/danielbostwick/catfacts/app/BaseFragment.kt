package com.danielbostwick.catfacts.app

import android.util.Log
import com.trello.rxlifecycle.components.support.RxFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

abstract class BaseFragment : RxFragment() {

    abstract val TAG: String

    init {
        lifecycle()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d(TAG, "lifecycle event : $it") },
                        { t -> Log.e(TAG, Log.getStackTraceString(t)) })
    }
}
