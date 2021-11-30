package com.chatbooks.chatter.internal.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.chatbooks.chatter.api.Chatter
import com.chatbooks.chatter.internal.ui.error.ErrorListFragment
import com.chatbooks.chatter.internal.ui.generic.GenericListFragment
import com.chatbooks.chatter.internal.ui.transaction.TransactionListFragment

/**
 * @author Jordan Carlson
 */
internal class HomePageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val screen = Chatter.screens[position]
        return when (screen.screenType) {
            Chatter.ScreenType.HTTP -> TransactionListFragment.newInstance()
            Chatter.ScreenType.CRASHES -> ErrorListFragment.newInstance()
            Chatter.ScreenType.GENERIC -> GenericListFragment.newInstance(screen.name)
        }
    }

    override fun getCount(): Int {
        return Chatter.screens.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val screen = Chatter.screens[position]
        return screen.name
    }
}
