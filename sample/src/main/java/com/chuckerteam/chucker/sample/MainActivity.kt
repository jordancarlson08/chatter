package com.chuckerteam.chucker.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.api.Chatter
import com.chuckerteam.chucker.api.ChatterCollector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var client: HttpBinClient

    var toggle = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        client = HttpBinClient(applicationContext)

        do_http.setOnClickListener { client.doHttpActivity() }
        trigger_exception.setOnClickListener { client.recordException() }

        val collector = ChatterCollector(this)
        triggerGeneric.setOnClickListener {
            if (toggle) {
                toggle = !toggle
                collector.onGeneric(Chatter.Screen.ANALYTICS, "Generic Title", "generic subtitle", "some message", "Some content that could potentially be very long")
            } else {
                toggle = !toggle
                collector.onGeneric(Chatter.Screen.SESSION, "Generic Session Title", "generic session subtitle", "some message", "Some content that could potentially be very long")
            }
        }


        with(launch_chucker_directly) {
            visibility = if (Chatter.isOp) View.VISIBLE else View.GONE
            setOnClickListener { launchChuckerDirectly() }
        }

        client.initializeCrashHandler()
    }

    private fun launchChuckerDirectly() {
        // Optionally launch Chucker directly from your own app UI
        startActivity(Chatter.getLaunchIntent(this, Chatter.Screen.HTTP))
    }
}
