package ir.sass.navigator

import androidx.core.os.bundleOf
import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        is NavigationFlow.AboutUsFlow->{

        }
        is NavigationFlow.TrackFlow->{
            navController.navigate(R.id.global_action_to_track_nav, bundleOf().apply {
                putInt("id",navigationFlow.id)
            })
        }
    }

    fun resetNavigation(){
        navController.setGraph(R.navigation.app_navigation)
    }
}