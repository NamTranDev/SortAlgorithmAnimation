package dev.tran.nam.sortalgorithm.widget

import java.io.Serializable

enum class SortType(val value: Int) : Serializable {
    SELECTIONSORT(1), INSERTIONSORTI(2), INSERTIONSORTII(3), BUBBLESORT(4), QUICKSORT(5)
}