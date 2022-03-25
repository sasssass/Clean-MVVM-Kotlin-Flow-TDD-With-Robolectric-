package ir.sass.baseui.util

import android.widget.SeekBar
import androidx.databinding.BindingAdapter

@BindingAdapter("app:position")
fun SeekBar.changePosition(pos : Int){
    progress = pos
}