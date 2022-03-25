package ir.track.ui.fragment.main

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.baseui.MotherFragment
import ir.sass.baseui.util.getPermissions
import ir.sass.baseui.util.toast
import ir.sass.track.ui.R
import ir.track.domain.model.TrackModel
import ir.sass.track.ui.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : MotherFragment<FragmentDetailBinding>(
    MotherFragmentSetting(R.layout.fragment_detail,"Detail")
){

    override val viewModel: DetailFragmentViewModel by viewModels()
    private var downLoadId = 0L
    lateinit var downloadManager : DownloadManager
    var uriDownload : Uri? = null

    val args by navArgs<DetailFragmentArgs>()

    @RequiresApi(Build.VERSION_CODES.M)
    val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val settingsCanWrite: Boolean = Settings.System.canWrite(requireContext())
        if(settingsCanWrite){
            uriDownload?.let { changeRingtone(it) }
        }else{
            requireContext().toast("You didn't provide permission")
        }
    }


    override fun binding() {
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.getPermissionAndDownload = getPermissionAndDownload

        requireActivity().registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        dataBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, isUser: Boolean) {
                if(isUser){
                    viewModel.seekInto(pos)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        viewModel.getData(args.id)
    }

    val getPermissionAndDownload : () -> Unit = {
        lifecycleScope.launch {
            getPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)).collect {
                if(it.granted){
                    downLoad(viewModel.data.value!!) // on this step we are sure data is not null
                }else{
                    requireContext().toast("Permission is not granted")
                }
            }
        }
    }

    private fun downLoad(data : TrackModel){
        downloadManager = requireActivity().getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val Download_Uri: Uri =
            Uri.parse(data.preview)
        val request = DownloadManager.Request(Download_Uri)
        request.setTitle("RingTonePicker")
        request.setDescription("Downloading : ${data.title} from ${data.artist.name}")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downLoadId = downloadManager.enqueue(request)
    }

    private var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            if(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1) == downLoadId){
                val mostRecentDownload =
                    downloadManager.getUriForDownloadedFile(downLoadId)
                uriDownload = mostRecentDownload
                requestForChangingRingtone()
            }
        }
    }

    fun requestForChangingRingtone(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val settingsCanWrite: Boolean = Settings.System.canWrite(requireContext())
            if(settingsCanWrite){
                uriDownload?.let { changeRingtone(it) }
            }else{
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + requireActivity().packageName))
                result.launch(intent)
            }
        }else{
            uriDownload?.let { changeRingtone(it) }
        }
    }

    private fun changeRingtone(uri : Uri){
        RingtoneManager.setActualDefaultRingtoneUri(
            requireActivity(),
            RingtoneManager.TYPE_RINGTONE,
            uri
        )
        requireContext().toast("Ringtone is changed")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(onComplete)
    }
}