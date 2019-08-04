package dev.tran.nam.sortalgorithm.widget

import android.graphics.Rect
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SortValue(var rect: Rect? = null,
                     var value: Int = 0,
                     var isSort: Boolean = false) : Parcelable