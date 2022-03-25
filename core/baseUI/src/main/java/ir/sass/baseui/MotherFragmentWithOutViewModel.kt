package ir.sass.baseui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ir.sass.baseui.databinding.FragmentBaseBinding
import ir.sass.navigator.NavigationFlow
import ir.sass.navigator.ToFlowNavigatable
import kotlinx.coroutines.launch


abstract class MotherFragmentWithOutViewModel<DataBinding : ViewDataBinding>(
    val setting : MotherFragmentWithOutViewModel.MotherFragmentSetting
) : Fragment(R.layout.fragment_base) {
    lateinit var flowNavigator : ToFlowNavigatable
    internal lateinit var dataBindingOuter : FragmentBaseBinding
    protected lateinit var dataBinding : DataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBindingOuter = FragmentBaseBinding.inflate(inflater)
        dataBindingOuter.title = setting.title
        return dataBindingOuter.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),setting.layout,dataBindingOuter.linAdd,true
        )
        flowNavigator = requireActivity() as ToFlowNavigatable
        binding()
    }

    abstract fun binding()

    class MotherFragmentSetting(
        @LayoutRes
        val layout : Int,
        val title : String
    )

    fun navigate(flow: NavigationFlow){
        flowNavigator.navigateToFlow(flow)
    }

    fun hideKeyboard() {
        val activity = requireActivity()
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun coroutinesLauncherWithCreatedState(action :suspend () -> Unit){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                action.invoke()
            }

        }
    }
}