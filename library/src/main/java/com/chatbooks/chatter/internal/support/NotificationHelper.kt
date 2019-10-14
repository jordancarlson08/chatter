/*
 * Copyright (C) 2017 Jeff Gilfelt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chatbooks.chatter.internal.support

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.LongSparseArray
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

import com.chatbooks.chatter.R
import com.chatbooks.chatter.api.Chatter
import com.chatbooks.chatter.internal.data.entity.Generic
import com.chatbooks.chatter.internal.data.entity.HttpTransaction
import com.chatbooks.chatter.internal.data.entity.RecordedThrowable
import com.chatbooks.chatter.internal.ui.BaseChatterActivity

import java.util.HashSet

class NotificationHelper(private val context: Context) {

    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID,
                            context.getString(R.string.chatter_notification_category),
                            NotificationManager.IMPORTANCE_LOW))
        }
    }

    fun show(transaction: HttpTransaction) {
        addToBuffer(transaction)
        if (!BaseChatterActivity.isInForeground) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(PendingIntent.getActivity(context, TRANSACTION_NOTIFICATION_ID, Chatter.getLaunchIntent(context, Chatter.Screen.HTTP), PendingIntent.FLAG_UPDATE_CURRENT))
                    .setLocalOnly(true)
                    .setSmallIcon(R.drawable.chatter_ic_notification)
                    .setColor(ContextCompat.getColor(context, R.color.chatter_primary_color))
                    .setContentTitle(context.getString(R.string.chatter_http_notification_title))
                    .addAction(createClearAction(ClearDatabaseService.ClearAction.Transaction))
            val inboxStyle = NotificationCompat.InboxStyle()
            synchronized(transactionBuffer) {
                var count = 0
                for (i in transactionBuffer.size() - 1 downTo 0) {
                    if (count < BUFFER_SIZE) {
                        if (count == 0) {
                            builder.setContentText(transactionBuffer.valueAt(i).notificationText)
                        }
                        inboxStyle.addLine(transactionBuffer.valueAt(i).notificationText)
                    }
                    count++
                }
                builder.setAutoCancel(true)
                builder.setStyle(inboxStyle)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setSubText(transactionIdsSet.size.toString())
                } else {
                    builder.setNumber(transactionIdsSet.size)
                }
            }
            notificationManager.notify(TRANSACTION_NOTIFICATION_ID, builder.build())
        }
    }

    fun show(throwable: RecordedThrowable) {
        if (!BaseChatterActivity.isInForeground) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(PendingIntent.getActivity(context, ERROR_NOTIFICATION_ID, Chatter.getLaunchIntent(context, Chatter.Screen.ERROR), PendingIntent.FLAG_UPDATE_CURRENT))
                    .setLocalOnly(true)
                    .setSmallIcon(R.drawable.chatter_ic_subject_white_24dp)
                    .setColor(ContextCompat.getColor(context, R.color.chatter_status_error))
                    .setContentTitle(throwable.clazz)
                    .setAutoCancel(true)
                    .setContentText(throwable.message)
                    .addAction(createClearAction(ClearDatabaseService.ClearAction.Error))
            notificationManager.notify(ERROR_NOTIFICATION_ID, builder.build())
        }
    }

    fun show(generic: Generic) {
        if (!BaseChatterActivity.isInForeground) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(PendingIntent.getActivity(context, GENERIC_NOTIFICATION_ID, Chatter.getLaunchIntent(context, Chatter.Screen.EVENTS), PendingIntent.FLAG_UPDATE_CURRENT))
                    .setLocalOnly(true)
                    .setSmallIcon(R.drawable.chatter_ic_subject_white_24dp)
                    .setColor(ContextCompat.getColor(context, R.color.chatter_status_error))
                    .setContentTitle(generic.title)
                    .setAutoCancel(true)
                    .setContentText(generic.message)
                    .addAction(createClearAction(ClearDatabaseService.ClearAction.Error))
            notificationManager.notify(GENERIC_NOTIFICATION_ID, builder.build())
        }
    }

    private fun createClearAction(clearAction: ClearDatabaseService.ClearAction): NotificationCompat.Action {
        val clearTitle = context.getString(R.string.chatter_clear)
        val deleteIntent = Intent(context, ClearDatabaseService::class.java)
        deleteIntent.putExtra(ClearDatabaseService.EXTRA_ITEM_TO_CLEAR, clearAction)
        val intent = PendingIntent.getService(context, 11, deleteIntent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Action(R.drawable.chatter_ic_delete_white_24dp, clearTitle, intent)
    }

    fun dismissTransactionsNotification() {
        notificationManager.cancel(TRANSACTION_NOTIFICATION_ID)
    }

    fun dismissErrorsNotification() {
        notificationManager.cancel(ERROR_NOTIFICATION_ID)
    }

    companion object {

        private val CHANNEL_ID = "chatter"
        private val TRANSACTION_NOTIFICATION_ID = 1138
        private val ERROR_NOTIFICATION_ID = 3546
        private val GENERIC_NOTIFICATION_ID = 3547
        private val BUFFER_SIZE = 10

        private val transactionBuffer = LongSparseArray<HttpTransaction>()
        // Set of all the seen transaction IDs. Used to update the notification count.
        private val transactionIdsSet = HashSet<Long>()

        fun clearBuffer() {
            synchronized(transactionBuffer) {
                transactionBuffer.clear()
                transactionIdsSet.clear()
            }
        }

        private fun addToBuffer(transaction: HttpTransaction) {
            if (transaction.id == 0L) {
                // Don't store Transactions with an invalid ID (0).
                // Transaction with an Invalid ID will be shown twice in the notification
                // with both the invalid and the valid ID and we want to avoid this.
                return
            }
            synchronized(transactionBuffer) {
                transactionIdsSet.add(transaction.id)
                transactionBuffer.put(transaction.id, transaction)
                if (transactionBuffer.size() > BUFFER_SIZE) {
                    transactionBuffer.removeAt(0)
                }
            }
        }
    }
}
