package ir.sass.baseui.util

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@BindingAdapter("ext:textWatcher")
fun setTextWatcher(editText : EditText,input : MutableSharedFlow<String>){
    editText.addTextChangedListener {
        CoroutineScope(Main).launch {
            input.emit(it.toString())
        }
    }
}