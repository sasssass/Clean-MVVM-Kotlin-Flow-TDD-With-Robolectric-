package ir.sass.baseui.util

import android.view.View
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@BindingAdapter("ext:active")
fun active(view : View,active : Boolean){
    if(active){
        view.isEnabled = true
        view.alpha = 1f
    }else{
        view.isEnabled = false
        view.alpha = 0.7f
    }
}


@BindingAdapter("ext:click")
fun setClick(view : View,action : () ->  Unit){
    view.setOnClickListener {
        CoroutineScope(Dispatchers.Main).launch {
            it.isClickable = false
            action.invoke()
            delay(200)
            it.isClickable = true
        }
    }
}