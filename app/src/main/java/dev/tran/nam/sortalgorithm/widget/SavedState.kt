package dev.tran.nam.sortalgorithm.widget

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class SavedState : View.BaseSavedState {

    var begin: Int = 0
    var insertionsortTemp: Int = 0
    var temp: Int = 0

    var isAnimation: Boolean = false
    var isStartAnimation: Boolean = false
    var insertionWhile: Boolean = false
    var insertionNotSwap: Boolean = false

    var mDistance: Int = 0

    var sortValues: ArrayList<SortValue>? = null

    constructor(superState: Parcelable?) : super(superState)

    private constructor(`in`: Parcel) : super(`in`) {
        this.begin = `in`.readInt()
        this.insertionsortTemp = `in`.readInt()
        this.temp = `in`.readInt()
        this.mDistance = `in`.readInt()
        this.isAnimation = `in`.readByte().toInt() != 0
        this.isStartAnimation = `in`.readByte().toInt() != 0
        this.insertionWhile = `in`.readByte().toInt() != 0
        this.insertionNotSwap = `in`.readByte().toInt() != 0

        `in`.readList(this.sortValues!! as List<*>, SortValue::class.java.classLoader)
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(this.begin)
        out.writeInt(this.insertionsortTemp)
        out.writeInt(this.temp)
        out.writeInt(this.mDistance)
        out.writeByte((if (isAnimation) 1 else 0).toByte())
        out.writeByte((if (isStartAnimation) 1 else 0).toByte())
        out.writeByte((if (insertionWhile) 1 else 0).toByte())
        out.writeByte((if (insertionNotSwap) 1 else 0).toByte())

        out.writeList(sortValues as List<*>?)
    }

    @JvmField
    val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

        override fun createFromParcel(source: Parcel): SavedState {
            return SavedState(source)
        }

        override fun newArray(size: Int): Array<SavedState?> {
            return arrayOfNulls(size)
        }
    }
}