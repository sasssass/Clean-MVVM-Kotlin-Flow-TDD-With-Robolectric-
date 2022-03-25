package ir.track.ui.fragment.main

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sass.baseui.MotherFragmentViewModel
import ir.track.domain.model.PlayState
import ir.track.domain.model.TrackModel
import ir.track.domain.usecase.GetTrackUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(
    private val getTrackUseCase: GetTrackUseCase
) : MotherFragmentViewModel() {

    private var mediaPlayer : MediaPlayer? = null

    fun setMediaPlayer(mediaPlayer: MediaPlayer){
        this.mediaPlayer = mediaPlayer
    }

    private val _data : MutableStateFlow<TrackModel?> = MutableStateFlow(null)
    val data : StateFlow<TrackModel?> = _data

    private val _currentTime : MutableStateFlow<Int> = MutableStateFlow(0)
    val currentTime : StateFlow<Int> = _currentTime

    private val _duration : MutableStateFlow<Int> = MutableStateFlow(0)
    val duration : StateFlow<Int> = _duration

    private val _playState : MutableStateFlow<PlayState> = MutableStateFlow(PlayState.NOT_INIT)
    val playState : StateFlow<PlayState> = _playState

    private val _isActive : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isActive : StateFlow<Boolean> = _isActive

    private val job = SupervisorJob()

    init {
        CoroutineScope(Main + job).launch {
            while (isActive){
                delay(1000)
                mediaPlayer?.let {
                    _currentTime.emit(it.currentPosition/1000)
                }
            }
        }
    }

    fun getData(id : Int){
        action(getTrackUseCase.invoke(id),true){
            viewModelScope.launch {
                _isActive.emit(true)
                _data.emit(it)
            }
        }
    }


    fun play(){
        when(playState.value){
            PlayState.PLAYING->{
                pause()
            }
            PlayState.PAUSE->{
                resume()
            }
            PlayState.DONE->{
                mediaPlayer?.seekTo(0)
                mediaPlayer?.start()
                viewModelScope.launch {
                    _playState.emit(PlayState.PLAYING)
                }
            }
            PlayState.NOT_INIT->{
                initPlayer()
            }
        }

    }

    fun initPlayer(){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build())
        }else{
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
        }
        mediaPlayer!!.setDataSource(data.value!!.preview)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()

        mediaPlayer!!.setOnCompletionListener {
            viewModelScope.launch {
                _playState.emit(PlayState.DONE)
            }
        }

        viewModelScope.launch {
            _duration.emit((mediaPlayer!!.duration/1000))
        }

        changeStateToPlaying()
    }

    fun changeStateToPlaying(){
        viewModelScope.launch {
            _playState.emit(PlayState.PLAYING)
        }
    }

    fun pause(){
        mediaPlayer?.let {
            if(it.isPlaying){
                viewModelScope.launch {
                    _playState.emit(PlayState.PAUSE)
                }
            }
            it.pause()
        }
        
    }
    
    fun resume(){
        mediaPlayer?.let {
            if(!it.isPlaying){
                viewModelScope.launch {
                    _playState.emit(PlayState.PLAYING)
                }
            }
            it.start()
        }
    }

    public override fun onCleared() {
        super.onCleared()
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        job.cancel()
    }

    fun seekInto(pos: Int) {
        if(playState.value == PlayState.DONE){
            viewModelScope.launch {
                _playState.emit(PlayState.PAUSE)
            }
        }
        mediaPlayer?.seekTo(pos * 1000)
    }
}