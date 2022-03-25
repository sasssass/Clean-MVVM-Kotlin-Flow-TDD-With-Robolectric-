package ir.search.ui.fragment.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sass.baseui.MotherFragmentViewModel
import ir.search.domain.model.SearchModel
import ir.search.domain.usecase.SearchAmongSongUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchAmongSongUseCase : SearchAmongSongUseCase
) : MotherFragmentViewModel()  {

    val activeButton : MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _data : MutableStateFlow<SearchModel?> = MutableStateFlow(null)
    val data : StateFlow<SearchModel?> = _data

    private val _hideKeyBoard : MutableSharedFlow<Boolean> = MutableSharedFlow()
    val hideKeyBoard : SharedFlow<Boolean> = _hideKeyBoard

    val textInput : MutableStateFlow<String> = MutableStateFlow("")

    init {
        viewModelScope.launch {
            launch {
                activeButton.emit(false)
            }
            launch {
                textInput.collect {
                    activeButton.emit(it.isNotEmpty())
                }
            }
        }
    }

    fun search(){
        viewModelScope.launch {
            _hideKeyBoard.emit(true)
        }
        val input = textInput.value
        textInput.value = ""
        action(searchAmongSongUseCase(input),true){
            viewModelScope.launch {
                _data.emit(it)
            }
        }
    }
}