package ir.track

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.navigator.NavigationFlow
import ir.sass.navigator.ToFlowNavigatable

@AndroidEntryPoint
class HiltAndroidActivityTrackTest : AppCompatActivity() , ToFlowNavigatable {
    override fun navigateToFlow(flow: NavigationFlow) {

    }
}