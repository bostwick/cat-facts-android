package com.danielbostwick.catfacts.app

import android.app.Application
import android.os.StrictMode
import com.danielbostwick.catfacts.BuildConfig

class CatFactsApp : Application() {

    companion object {
        val TAG = CatFactsApp::class.java.simpleName
        lateinit var components: CatFactsComponent
    }

    override fun onCreate() {
        super.onCreate()

        components = CatFactsComponent()

        initStrictMode()
    }

    fun initStrictMode() {
        val threadPolicy = StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()

        val vmPolicy = StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()

        if (BuildConfig.DEBUG) {
            threadPolicy
//                    .penaltyDeathOnNetwork()
                    .penaltyLog()
                    .penaltyFlashScreen()
        }

        StrictMode.setThreadPolicy(threadPolicy.build())
        StrictMode.setVmPolicy(vmPolicy.build())
    }
}
