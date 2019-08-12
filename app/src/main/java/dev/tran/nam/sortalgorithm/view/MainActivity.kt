package dev.tran.nam.sortalgorithm.view

import android.widget.Button
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.RadioGroupPlus
import dev.tran.nam.sortalgorithm.widget.SortType
import dev.tran.nam.sortalgorithm.widget.SortView
import dev.tran.nam.sortalgorithm.widget.SortViewListener
import tran.nam.core.view.BaseActivityInjection
import tran.nam.util.StatusBarUtil

class MainActivity : BaseActivityInjection() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this,ContextCompat.getColor(this,R.color.lico_rice))
    }
}
