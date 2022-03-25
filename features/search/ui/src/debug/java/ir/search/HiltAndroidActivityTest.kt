package ir.search

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.navigator.NavigationFlow
import ir.sass.navigator.ToFlowNavigatable

@AndroidEntryPoint
class HiltAndroidActivityTest : AppCompatActivity() , ToFlowNavigatable {
    override fun navigateToFlow(flow: NavigationFlow) {

    }
}