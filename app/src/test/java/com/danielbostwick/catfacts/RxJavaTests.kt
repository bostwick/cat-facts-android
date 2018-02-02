package com.danielbostwick.catfacts

import org.junit.Test
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class RxJavaTests {

    private fun printWithThread(msg: String) {
        println("[${Thread.currentThread().name}] $msg")
    }

    private fun square(n: Int) = n * n
    private fun isEven(n: Int) = n % 2 == 0

    @Test
    fun rxJavaIsSingleThreadedByDefault() {
        Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .doOnNext { printWithThread("source: $it") }
                .map(::square)
                .doOnNext { printWithThread("squared: $it") }
                .filter(::isEven)
                .doOnNext { printWithThread("evensOnly: $it") }
                .subscribe { printWithThread("observed: $it") }
    }

    @Test
    fun rxJavaCanSwitchThreadsAtWill() {
        val countDownLatch = CountDownLatch(1)
        val myScheduler = Schedulers.from(Executors.newSingleThreadExecutor(object : ThreadFactory {
            override fun newThread(r: Runnable?) = Thread(r, "myThread")
        }))

        Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                // Emitted on a new Thread
                .observeOn(Schedulers.newThread())
                .doOnNext { printWithThread("source: $it") }

                // Square on the IO thread
                .observeOn(Schedulers.io())
                .map(::square)
                .doOnNext { printWithThread("squared: $it") }

                // filter on the computation thread
                .observeOn(Schedulers.computation())
                .filter(::isEven)
                .doOnNext { printWithThread("evensOnly: $it") }

                // Observe the final result on my own scheduler
                .observeOn(myScheduler)
                .doOnUnsubscribe { countDownLatch.countDown() }
                .subscribe { printWithThread("observed: $it") }

        countDownLatch.await()
    }

}