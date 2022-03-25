package ir.search.ui.fragment.hint

import ir.sass.baseui.MotherBottomSheetWithOutViewModel
import ir.sass.search.ui.R
import ir.sass.search.ui.databinding.BottomSheetHintBinding

class HintBottomSheet : MotherBottomSheetWithOutViewModel<BottomSheetHintBinding>(
    MotherBottomSheetSetting(R.layout.bottom_sheet_hint,"Hint")
) {
    override fun binding() {
    }
}