package com.example.blockchain.ui.main

import android.content.Context
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.blockchain.FragmentHoldings
import com.example.blockchain.FragmentMarket
import com.example.blockchain.FragmentNews
import com.example.blockchain.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        var newFrag:Fragment = Fragment()
        if (position == 0) {
            newFrag = FragmentNews()
            //return PlaceholderFragment.newInstance(position + 1)
        }
        if (position == 1) {
            newFrag = FragmentHoldings()
        }
        if (position == 2) {
            newFrag = FragmentMarket()
        }
        return newFrag
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}