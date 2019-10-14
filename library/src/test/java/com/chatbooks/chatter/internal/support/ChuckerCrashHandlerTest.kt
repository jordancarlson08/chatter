package com.chatbooks.chatter.internal.support

import com.chatbooks.chatter.api.ChatterCollector
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ChatterCrashHandlerTest {

    @Test
    fun uncaughtException_isReportedCorrectly() {
        val mockCollector = mockk<ChatterCollector>()
        val mockThrowable = Throwable()
        val handler = ChatterCrashHandler(mockCollector)
        every { mockCollector.onError(any(), any()) } returns Unit

        handler.uncaughtException(Thread.currentThread(), mockThrowable)

        verify { mockCollector.onError("Error caught on ${Thread.currentThread().name} thread", mockThrowable) }
    }
}