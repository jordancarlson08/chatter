package com.chatbooks.chatter.api

import android.content.Context

/**
 * No-op implementation.
 */
class ChatterCollector @JvmOverloads constructor(
        context: Context,
        var showNotification: Boolean = true,
        var retentionPeriod: RetentionManager.Period = RetentionManager.Period.ONE_WEEK
) {

    fun onError(obj: Any?, obj2: Any?) {
        // Empty method for the library-no-op artifact
    }

    fun onGeneric(obj1: Any?, obj2: Any?, obj3: Any?, obj4: Any?, obj5: Any?) {
        // Empty method for the library-no-op artifact
    }
}
