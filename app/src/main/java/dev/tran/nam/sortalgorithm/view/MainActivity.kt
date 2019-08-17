package dev.tran.nam.sortalgorithm.view

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.RadioGroupPlus
import dev.tran.nam.sortalgorithm.widget.SortType
import dev.tran.nam.sortalgorithm.widget.SortView
import dev.tran.nam.sortalgorithm.widget.SortViewListener
import io.fabric.sdk.android.Fabric
import tran.nam.core.view.BaseActivityInjection
import tran.nam.util.StatusBarUtil

class MainActivity : BaseActivityInjection() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this,ContextCompat.getColor(this,R.color.lico_rice))
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        val core = CrashlyticsCore.Builder().build()
        val kit = Crashlytics.Builder().core(core).build()
        Fabric.with(this, kit)
    }
}
