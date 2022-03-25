package ir.sass.baseui

import androidx.lifecycle.ViewModel
import ir.sass.basedomain.model.Domain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class MotherFragmentViewModel : ViewModel() {

    private val _loading : MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading : SharedFlow<Boolean> = _loading

    private val _error : MutableSharedFlow<String> = MutableSharedFlow()
    val error : SharedFlow<String> = _error

    fun <T>action(result : Flow<Domain<T>>,loading : Boolean, action : (T) -> Unit){
        viewModelScope.launch {
            result.collect { it ->
                if(loading && it is Domain.Progress){
                     loading(true)
                }else{
                    loading(false)
                    if(it is Domain.Result){
                        if(it.data.isSuccess){
                            it.data.getOrNull()?.let {
                                action.invoke(it)
                            }
                        }else{
                            handleError(it.data.exceptionOrNull()?: Throwable("Error"))
                        }
                    }
                }
            }
        }
    }

    private fun handleError(t : Throwable){
        viewModelScope.launch {
            _error.emit(t.message?:"Error")
        }
    }
    private fun loading(active : Boolean){
        viewModelScope.launch {
            _loading.emit(active)
        }
    }
}