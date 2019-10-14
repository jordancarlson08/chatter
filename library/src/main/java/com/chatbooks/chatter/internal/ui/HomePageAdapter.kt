package com.chatbooks.chatter.internal.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.chatbooks.chatter.api.Chatter
import com.chatbooks.chatter.internal.ui.error.ErrorListFragment
import com.chatbooks.chatter.internal.ui.generic.GenericListFragment
import com.chatbooks.chatter.internal.ui.transaction.TransactionListFragment

/**
 * @author Olivier Perez
 */
internal class HomePageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Chatter.Screen.SESSION.ordinal -> GenericListFragment.newInstance(position)
            Chatter.Screen.HTTP.ordinal -> TransactionListFragment.newInstance()
            Chatter.Screen.ERROR.ordinal -> ErrorListFragment.newInstance()
            Chatter.Screen.EVENTS.ordinal -> GenericListFragment.newInstance(position)
            Chatter.Screen.STRINGS.ordinal -> GenericListFragment.newInstance(position)
            else -> TransactionListFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return Chatter.Screen.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            Chatter.Screen.SESSION.ordinal -> Chatter.Screen.values()[position].name
            Chatter.Screen.HTTP.ordinal -> Chatter.Screen.values()[position].name
            Chatter.Screen.ERROR.ordinal -> Chatter.Screen.values()[position].name
            Chatter.Screen.EVENTS.ordinal -> Chatter.Screen.values()[position].name
            Chatter.Screen.STRINGS.ordinal -> Chatter.Screen.values()[position].name
            else -> Chatter.Screen.values()[position].name
        }
    }
}
