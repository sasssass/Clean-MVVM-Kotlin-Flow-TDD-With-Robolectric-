package ir.sass.baseui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.repeatOnLifecycle
import ir.sass.baseui.util.toast

abstract class MotherFragment<DataBinding : ViewDataBinding>(
    setting : MotherFragmentWithOutViewModel.MotherFragmentSetting
) : MotherFragmentWithOutViewModel<DataBinding>(setting) {

    protected abstract val viewModel : MotherFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBindingOuter.loading = false

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                launch {
                    viewModel.loading.collect{
                        dataBindingOuter.loading = it
                    }
                }
                launch {
                    viewModel.error.collect{
                        requireContext().toast(it)
                    }
                }
            }
        }
    }
}