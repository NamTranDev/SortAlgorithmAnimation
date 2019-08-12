@file:Suppress("DEPRECATION")

package dev.tran.nam.sortalgorithm.binding

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.widget.SortType
import tran.nam.Logger
import tran.nam.util.DisplayUtil


object BindingCommon {

    @JvmStatic
    @BindingAdapter("loadTextSortTitle")
    fun loadTextSortTitle(text: TextView, type: SortType?) {
        type?.run {
            text.textSize = 30f * DisplayUtil.scaleDensity(text.context)
            when (this) {
                SortType.SELECTIONSORT -> {
                    text.text = text.context.getString(R.string.selection_sort_title)
                }
                SortType.INSERTIONSORTI -> {
                    text.text = text.context.getString(R.string.insertion_sort_title)
                }
                SortType.BUBBLESORT -> {
                    text.text = text.context.getString(R.string.bubble_sort_title)
                }
                SortType.QUICKSORT -> {
                    text.text = text.context.getString(R.string.quick_sort_title)
                }
                else -> {
                    Logger.debug("Nothing")
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("loadTextSortDescription")
    fun loadTextSortDescription(text: TextView, type: SortType?) {
        type?.run {
            when (this) {
                SortType.SELECTIONSORT -> {
                    text.text = Html.fromHtml(text.context.getString(R.string.selection_sort_description))
                }
                SortType.INSERTIONSORTI -> {
                    text.text = Html.fromHtml(text.context.getString(R.string.insertion_sort_description))
                }
                SortType.BUBBLESORT -> {
                    text.text = Html.fromHtml(text.context.getString(R.string.bubble_sort_description))
                }
                SortType.QUICKSORT -> {
                    text.text = Html.fromHtml(text.context.getString(R.string.quick_sort_description))
                }
                else -> {
                    Logger.debug("Nothing")
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("loadImageSortDescription")
    fun loadImageSortDescription(image: ImageView, type: SortType?) {
        type?.run {
            when (this) {
                SortType.SELECTIONSORT -> {
                    image.setImageDrawable(ContextCompat.getDrawable(image.context, R.drawable.image_selection_sort))
                }
                SortType.INSERTIONSORTI -> {
                    image.setImageDrawable(ContextCompat.getDrawable(image.context, R.drawable.image_insertion_sort))
                }
                SortType.BUBBLESORT -> {
                    image.setImageDrawable(ContextCompat.getDrawable(image.context, R.drawable.image_bublle_sort))
                }
                SortType.QUICKSORT -> {
                    image.setImageDrawable(ContextCompat.getDrawable(image.context, R.drawable.image_quick_sort))
                }
                else -> {
                    Logger.debug("Nothing")
                }
            }
        }
    }
}