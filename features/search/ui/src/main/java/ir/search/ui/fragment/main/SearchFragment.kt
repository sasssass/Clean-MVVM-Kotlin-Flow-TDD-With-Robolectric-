package ir.search.ui.fragment.main

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.sass.baseui.MotherAdapter
import ir.sass.baseui.MotherFragment
import ir.sass.baseui.RecyclerItemWrapper
import ir.sass.navigator.NavigationFlow
import ir.sass.navigator.navigateInsideModule
import ir.sass.search.ui.R
import ir.sass.search.ui.databinding.FragmetSearchBinding
import ir.sass.search.ui.databinding.ItemSearchBinding
import ir.search.domain.model.SearchModelData
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment: MotherFragment<FragmetSearchBinding>(MotherFragmentSetting(R.layout.fragmet_search,"Search")) {
    override val viewModel: SearchFragmentViewModel by viewModels()
    override fun binding() {
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        val adapter = MotherAdapter<ItemSearchBinding,SearchModelData>(
            RecyclerItemWrapper(R.layout.item_search){ binding, item, pos ->
                binding.song = item
                binding.navigate = {
                    navigate(NavigationFlow.TrackFlow(item.id))
                }
            }
        )
        dataBinding.adapter = adapter
        dataBinding.goToHelp = {
            navigateInsideModule(R.id.hintBottomSheet)
        }

        coroutinesLauncherWithCreatedState{
            viewModel.data.collect {
                it?.let {
                    adapter.changeList(it.data_)
                }
            }
        }

        coroutinesLauncherWithCreatedState {
            viewModel.hideKeyBoard.collect {
                hideKeyboard()
            }
        }
    }
}