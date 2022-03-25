package ir.sass.baseui.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val PERMISSION_REQUEST_CODE = 100

val permissionListener = mutableMapOf<String,(Boolean) -> Unit>()

data class Permission(
    val title : String,
    val granted : Boolean
)

private val permissionFlow : MutableSharedFlow<Permission> = MutableSharedFlow()

fun getPermissions(activity: Activity, permissions: Array<String>) : SharedFlow<Permission> {
    CoroutineScope(Main).launch {
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                permissionFlow.emit(Permission(it, true))
            } else {
                permissionListener[it] = { granted ->
                    CoroutineScope(Main).launch {
                        permissionFlow.emit(Permission(it, granted))
                        permissionListener.remove(it)
                    }
                }
                requestPermission(activity, it)
            }
        }
    }
    return permissionFlow
}

fun resultOfPermission(title: String,granted : Boolean){
    permissionListener[title]?.invoke(granted)
}

private fun requestPermission(activity: Activity,permission: String){
    requestPermissions(activity,
        arrayOf(permission),
        PERMISSION_REQUEST_CODE)
}

fun getChangeSettingPermission(activity: AppCompatActivity) : SharedFlow<Boolean>{
    val permissionByActionIntentFlow : MutableSharedFlow<Boolean> = MutableSharedFlow()
    CoroutineScope(Main).launch {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val settingsCanWrite: Boolean = Settings.System.canWrite(activity)
            if (!settingsCanWrite) {

                var resultLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        permissionListener["Setting"]?.invoke(true)
                    }else{
                        permissionListener["Setting"]?.invoke(false)
                    }
                }
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + activity.packageName))
                resultLauncher.launch(intent)

                permissionListener["Setting"] = { granted ->
                    CoroutineScope(Main).launch {
                        permissionByActionIntentFlow.emit(true)
                        permissionListener.remove("Setting")
                    }
                }
            } else {
                permissionByActionIntentFlow.emit(true)
            }
        }else{
            permissionByActionIntentFlow.emit(true)
        }
    }
    return permissionByActionIntentFlow
}