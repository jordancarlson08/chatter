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
package com.chuckerteam.chucker.internal.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.api.Chatter
import com.chuckerteam.chucker.api.Chatter.Screen.HTTP
import com.chuckerteam.chucker.internal.ui.error.ErrorActivity
import com.chuckerteam.chucker.internal.ui.error.ErrorAdapter
import com.chuckerteam.chucker.internal.ui.generic.GenericAdapter
import com.chuckerteam.chucker.internal.ui.transaction.TransactionActivity
import com.chuckerteam.chucker.internal.ui.transaction.TransactionAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.chucker_activity_main.*

class MainActivity : BaseChuckerActivity(), TransactionAdapter.TransactionClickListListener, ErrorAdapter.ErrorClickListListener, GenericAdapter.OnClickListListener {

    private val applicationName: CharSequence
        get() {
            val applicationInfo = applicationInfo
            return applicationInfo.loadLabel(packageManager)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chucker_activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.subtitle = applicationName

        viewPager.adapter = HomePageAdapter(this, supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    Chatter.dismissTransactionsNotification(this@MainActivity)
                } else {
                    Chatter.dismissErrorsNotification(this@MainActivity)
                }
            }
        })
        consumeIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        consumeIntent(intent)
    }

    /**
     * Scroll to the right tab.
     */
    private fun consumeIntent(intent: Intent) {
        // Get the screen to show, by default => HTTP
        val screenOrdinal = intent.getIntExtra(EXTRA_SCREEN, HTTP.ordinal)
        viewPager.setCurrentItem(screenOrdinal, true)
    }

    override fun onErrorClick(throwableId: Long, position: Int) {
        startActivity(ErrorActivity.newInstance(this, throwableId))
    }

    override fun onTransactionClick(transactionId: Long, position: Int) {
        TransactionActivity.start(this, transactionId)
    }

    override fun onClick(throwableId: Long, position: Int) {

    }

    companion object {

        val EXTRA_SCREEN = "EXTRA_SCREEN"
    }
}
