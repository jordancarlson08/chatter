package com.chatbooks.chatter.internal.support

import com.chatbooks.chatter.api.ChatterCollector

class ChatterCrashHandler(private val collector: ChatterCollector) : Thread.UncaughtExceptionHandler {

    private val defaultHandler: Thread.UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        collector.onError("Error caught on ${thread.name} thread", throwable)
        defaultHandler?.uncaughtException(thread, throwable)
    }
}
