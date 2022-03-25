package ir.sass.navigator

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.sass.basedata.model.toJsonString
import ir.sass.basedata.model.toReal

fun <T>Fragment.navigateInsideModule(navId: Int,data : T){
    val converted = toJsonString(data)
    findNavController().navigate(navId,bundleOf().apply { putString(getString(R.string.arg),converted) })
}

fun Fragment.navigateInsideModule(navId: Int){
    findNavController().navigate(navId)
}

inline fun <reified T> Fragment.navArgsExt() =
    lazy(R::class) {
        val args = arguments ?: throw IllegalStateException("Fragment $this has null arguments")
        val json = args.get(getString(R.string.arg)) as String
        val convertedArgs = toReal<T>(json)
        convertedArgs
    }
