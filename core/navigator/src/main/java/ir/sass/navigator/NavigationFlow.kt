package ir.sass.navigator

sealed class NavigationFlow {
    object AboutUsFlow : NavigationFlow()
    class TrackFlow(val id : Int) : NavigationFlow()
}