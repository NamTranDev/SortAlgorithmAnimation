package dev.tran.nam.sortalgorithm.view.main

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import dev.tran.nam.sortalgorithm.view.sort.SortFragment

class SortFragmentPagerAdapter(
    fm: FragmentManager,
    override val baseElevation: Float,
    private val mFragments: MutableList<SortFragment>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT),
    ICarousel {

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getCardViewAt(position: Int): CardView {
        return mFragments[position].mCardView
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position)
        mFragments[position] = fragment as SortFragment
        return fragment
    }
}
