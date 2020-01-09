package com.chatbooks.chatter.api

import android.content.Context
import android.content.Intent

/**
 * No-op implementation.
 */
object Chatter {

    const val SCREEN_HTTP = 1
    const val SCREEN_ERROR = 2

    val isOp = false

    @JvmStatic fun getLaunchIntent(context: Context, screen: Int): Intent {
        return Intent()
    }

    @JvmStatic fun registerDefaultCrashHandler(collector: ChatterCollector) {
        // Empty method for the library-no-op artifact
    }

    @JvmStatic fun dismissTransactionsNotification(context: Context) {
        // Empty method for the library-no-op artifact
    }

    @JvmStatic fun dismissErrorsNotification(context: Context) {
        // Empty method for the library-no-op artifact
    }

    enum class Screen {
        SESSION,
        HTTP,
        CRASHES,
        EVENTS,
        APPSTRINGS
    }
}
