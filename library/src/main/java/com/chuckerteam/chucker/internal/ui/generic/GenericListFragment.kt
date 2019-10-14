package com.chuckerteam.chucker.internal.ui.generic

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider

internal class GenericListFragment : Fragment() {

    companion object {
        const val SCREEN = "SCREEN"

        @JvmStatic
        fun newInstance(screen: Int): GenericListFragment {
            val fragment = GenericListFragment()
            val args = Bundle()
            args.putInt(SCREEN, screen)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: GenericAdapter
    private lateinit var listener: GenericAdapter.OnClickListListener
    private lateinit var tutorialView: View
    private var screen = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chucker_fragment_error_list, container, false).apply {
            tutorialView = findViewById(R.id.tutorial)
            findViewById<TextView>(R.id.link).movementMethod = LinkMovementMethod.getInstance()

            val recyclerView = findViewById<RecyclerView>(R.id.list)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
            adapter = GenericAdapter(listener)
            recyclerView.adapter = adapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screen = arguments?.getInt(SCREEN) ?: 0

        require(context is GenericAdapter.OnClickListListener) {
            "Context must implement the listener."
        }
        listener = context

        RepositoryProvider.generic()
                .getSortedGenericsTuples(screen)
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
        inflater.inflate(R.menu.chucker_errors_list, menu)
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
                    .setTitle(R.string.chucker_clear)
                    .setMessage(R.string.chucker_clear_error_confirmation)
                    .setPositiveButton(R.string.chucker_clear) { _, _ ->
                        RepositoryProvider.generic().deleteAllGenerics()
                    }
                    .setNegativeButton(R.string.chucker_cancel, null)
                    .show()
        }
    }

}
