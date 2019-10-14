package com.chatbooks.chatter.internal.ui.error

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chatbooks.chatter.R
import com.chatbooks.chatter.internal.data.repository.RepositoryProvider

internal class ErrorListFragment : Fragment() {

    private lateinit var adapter: ErrorAdapter
    private lateinit var listener: ErrorAdapter.ErrorClickListListener
    private lateinit var tutorialView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chatter_fragment_error_list, container, false).apply {
            tutorialView = findViewById(R.id.tutorial)
            findViewById<TextView>(R.id.link).movementMethod = LinkMovementMethod.getInstance()

            val recyclerView = findViewById<RecyclerView>(R.id.list)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
            adapter = ErrorAdapter(listener)
            recyclerView.adapter = adapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        require(context is ErrorAdapter.ErrorClickListListener) {
            "Context must implement the listener."
        }
        listener = context

        RepositoryProvider.throwable()
            .getSortedThrowablesTuples()
            .observe(
                this,
                Observer { tuples ->
                    adapter.setData(tuples)
                    if (tuples.isNullOrEmpty()) {
                        tutorialView.visibility = View.VISIBLE
                    } else {
                        tutorialView.visibility = View.GONE
                    }
                }
            )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chatter_errors_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.clear) {
            askForConfirmation()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun askForConfirmation() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.chatter_clear)
                .setMessage(R.string.chatter_clear_error_confirmation)
                .setPositiveButton(R.string.chatter_clear) { _, _ ->
                    RepositoryProvider.throwable().deleteAllThrowables()
                }
                .setNegativeButton(R.string.chatter_cancel, null)
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ErrorListFragment()
    }
}
