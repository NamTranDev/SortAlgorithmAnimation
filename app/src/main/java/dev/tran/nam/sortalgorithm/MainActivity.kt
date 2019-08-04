package dev.tran.nam.sortalgorithm

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.view.SortType
import dev.tran.nam.sortalgorithm.view.SortView
import dev.tran.nam.sortalgorithm.view.SortViewListener

class MainActivity : AppCompatActivity(), SortViewListener {

    override fun startAnimation() {
        findViewById<Button>(R.id.btStartAnimation).isEnabled = false
        findViewById<RadioButton>(R.id.rb_selection_sort).isEnabled = false
        findViewById<RadioButton>(R.id.rb_insertion_sort_ii).isEnabled = false
        findViewById<RadioButton>(R.id.rb_insertion_sort_i).isEnabled = false
        findViewById<RadioButton>(R.id.rb_bubble_sort).isEnabled = false
        findViewById<RadioButton>(R.id.rb_quick_sort).isEnabled = false
    }

    override fun completeAnimation() {
        findViewById<Button>(R.id.btStartAnimation).isEnabled = true
        findViewById<RadioButton>(R.id.rb_selection_sort).isEnabled = true
        findViewById<RadioButton>(R.id.rb_insertion_sort_i).isEnabled = true
        findViewById<RadioButton>(R.id.rb_insertion_sort_ii).isEnabled = true
        findViewById<RadioButton>(R.id.rb_bubble_sort).isEnabled = true
        findViewById<RadioButton>(R.id.rb_quick_sort).isEnabled = true
    }

    lateinit var sortView: SortView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sortView = findViewById(R.id.sortView)
        sortView.setListValue(intArrayOf(5, 8, 2, 0, 5, 12, 8, 1, 15, 4))

        sortView.setSortViewListener(this)

        (findViewById<RadioButton>(R.id.rb_selection_sort)).isChecked = true

        (findViewById<RadioGroupPlus>(R.id.rg_sort)).setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_selection_sort -> sortView.setTypeSort(SortType.SELECTIONSORT)
                R.id.rb_insertion_sort_i -> sortView.setTypeSort(SortType.INSERTIONSORTI)
                R.id.rb_insertion_sort_ii -> sortView.setTypeSort(SortType.INSERTIONSORTII)
                R.id.rb_bubble_sort -> sortView.setTypeSort(SortType.BUBBLESORT)
                R.id.rb_quick_sort -> sortView.setTypeSort(SortType.QUICKSORT)
            }
        }

        findViewById<Button>(R.id.btStartAnimation).setOnClickListener {
            sortView.startAnimation()
        }
    }



    override fun onResume() {
        super.onResume()
        sortView.resumeAnimation()
    }

    override fun onPause() {
        super.onPause()
        sortView.pauseAnimation()
    }
}
