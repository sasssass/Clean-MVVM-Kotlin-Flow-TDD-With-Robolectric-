package ir.sass.baseui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.sass.baseui.databinding.BottomSheetBaseBinding
import ir.sass.navigator.ToFlowNavigatable

abstract class MotherBottomSheetWithOutViewModel<DataBinding : ViewDataBinding>(
    val setting : MotherBottomSheetSetting
) : BottomSheetDialogFragment() {

    lateinit var flowNavigator : ToFlowNavigatable
    internal lateinit var dataBindingOuter : BottomSheetBaseBinding
    protected lateinit var dataBinding : DataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBindingOuter = BottomSheetBaseBinding.inflate(inflater)
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

    class MotherBottomSheetSetting(
        @LayoutRes
        val layout : Int,
        val title : String
    )
}