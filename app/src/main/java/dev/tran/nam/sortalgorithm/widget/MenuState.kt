package dev.tran.nam.sortalgorithm.widget

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class MenuState : View.BaseSavedState {

    var mListMenuItem: ArrayList<MenuCircle>? = null

    constructor(superState: Parcelable?) : super(superState)

    private constructor(`in`: Parcel) : super(`in`) {
        `in`.readList(this.mListMenuItem!! as List<*>, SortValue::class.java.classLoader)

    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeList(mListMenuItem as List<*>?)
    }

    @JvmField
    val CREATOR: Parcelable.Creator<MenuState> = object : Parcelable.Creator<MenuState> {

        override fun createFromParcel(source: Parcel): MenuState {
            return MenuState(source)
        }

        override fun newArray(size: Int): Array<MenuState?> {
            return arrayOfNulls(size)
        }
    }
}