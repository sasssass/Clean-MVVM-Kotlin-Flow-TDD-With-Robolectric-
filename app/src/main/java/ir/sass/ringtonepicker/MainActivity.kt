package ir.sass.ringtonepicker

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.baseui.util.PERMISSION_REQUEST_CODE
import ir.sass.baseui.util.resultOfPermission
import ir.sass.navigator.NavigationFlow
import ir.sass.navigator.Navigator
import ir.sass.navigator.ToFlowNavigatable
import ir.sass.ringtonepicker.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , ToFlowNavigatable {

    lateinit var dataBinding : ActivityMainBinding
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
        setContentView(dataBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navigator = Navigator().apply {
            navController = navHostFragment.navController
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    resultOfPermission(permissions[0],true)
                } else {
                    resultOfPermission(permissions[0],false)
                }
            }
        }
    }


    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }
}