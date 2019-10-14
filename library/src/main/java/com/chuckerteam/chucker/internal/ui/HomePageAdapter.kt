package com.chuckerteam.chucker.internal.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.chuckerteam.chucker.api.Chatter
import com.chuckerteam.chucker.internal.ui.error.ErrorListFragment
import com.chuckerteam.chucker.internal.ui.generic.GenericListFragment
import com.chuckerteam.chucker.internal.ui.transaction.TransactionListFragment
import java.lang.ref.WeakReference

/**
 * @author Olivier Perez
 */
internal class HomePageAdapter(context: Context, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val context: WeakReference<Context> = WeakReference(context)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Chatter.Screen.SESSION.ordinal -> GenericListFragment.newInstance(position)
            Chatter.Screen.HTTP.ordinal -> TransactionListFragment.newInstance()
            Chatter.Screen.ERROR.ordinal -> ErrorListFragment.newInstance()
            Chatter.Screen.ANALYTICS.ordinal -> GenericListFragment.newInstance(position)
            Chatter.Screen.APP_STRINGS.ordinal -> GenericListFragment.newInstance(position)
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
            Chatter.Screen.ANALYTICS.ordinal -> Chatter.Screen.values()[position].name
            Chatter.Screen.APP_STRINGS.ordinal -> Chatter.Screen.values()[position].name
            else -> Chatter.Screen.values()[position].name
        }
    }
}
