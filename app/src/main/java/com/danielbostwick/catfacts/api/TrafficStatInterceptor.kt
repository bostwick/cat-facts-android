package com.danielbostwick.catfacts.api

import android.net.TrafficStats
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TrafficStatInterceptor(private var trafficTag: Int) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        TrafficStats.setThreadStatsTag(trafficTag)

        return chain.proceed(chain.request())
    }
}
