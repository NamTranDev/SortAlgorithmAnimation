package tran.nam.util

import android.content.Context
import android.util.TypedValue

class DisplayUtil {

    companion object {
        private var deviceWidth = 0
        private var deviceHeight = 0

        fun getDeviceWidth(context: Context?): Int {
            if (deviceWidth == 0)
                deviceWidth = context?.resources?.displayMetrics?.widthPixels ?: 0
            return deviceWidth
        }

        fun getDeviceHeight(context: Context?): Int {
            if (deviceHeight == 0)
                deviceHeight = context?.resources?.displayMetrics?.heightPixels ?: 0
            return deviceHeight
        }

        fun convertDpToPx(context: Context?, dp: Float): Float {
            if (context == null) return 0f
            val r = context.resources
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, r.displayMetrics)
        }

        fun scaleDensity(context: Context): Float {
            val displayMetrics = context.resources.displayMetrics
            return displayMetrics.widthPixels.toFloat() / displayMetrics.density / 320f
        }
    }
}