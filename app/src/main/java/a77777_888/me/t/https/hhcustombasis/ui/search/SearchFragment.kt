package a77777_888.me.t.https.hhcustombasis.ui.search

import a77777_888.me.t.https.hhcustombasis.R
import a77777_888.me.t.https.hhcustombasis.databinding.FragmentSearchBinding
import a77777_888.me.t.https.hhcustombasis.model.entities.areas.Areas
import a77777_888.me.t.https.hhcustombasis.model.entities.areas.startWith
import a77777_888.me.t.https.hhcustombasis.model.settings.search.*
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.EXPERIENCE_1_3
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.EXPERIENCE_3_6
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.EXPERIENCE_MORE_THEN_6
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings.Companion.EXPERIENCE_NO_EXPERIENCE
import a77777_888.me.t.https.hhcustombasis.source.AreasProvider
import a77777_888.me.t.https.hhcustombasis.utils.hideSoftKeyboard
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    @Inject lateinit var settings: SearchSettings


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        initUI()
        binding.searchFAB.setOnClickListener { onSearchButtonPressed() }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun initUI() {
        with(settings) {

            findWords?.let {
                binding.findEditText.setText(findWords!!)
            }
            binding.findEditText.makeSoftKeyboardHidden()

            excludeWords?.let {
                binding.excludeEditText.setText(excludeWords!!)
            }
            binding.excludeEditText.makeSoftKeyboardHidden()

            binding.nameFieldSearchChip.isChecked = findIn.name
            binding.descriptionFieldSearchChip.isChecked = findIn.description
            binding.companyNameFieldSearchChip.isChecked = findIn.companyName

            regionInit()

            experience?.let {
                when (experience) {
                    EXPERIENCE_NO_EXPERIENCE -> binding.notMatterExperienceChip.isChecked = true
                    EXPERIENCE_1_3 -> binding.between1And3ExperienceChip.isChecked = true
                    EXPERIENCE_3_6 -> binding.between3And6ExperienceChip.isChecked = true
                    EXPERIENCE_MORE_THEN_6 -> binding.moreThan6ExperienceChip.isChecked = true
                }
            }

            binding.remoteScheduleChip.isChecked = schedule.remote
            binding.fullDayScheduleChip.isChecked = schedule.fullDay
            binding.shiftScheduleChip.isChecked = schedule.shift
            binding.flexibleScheduleChip.isChecked = schedule.flexible
            binding.flyInFlyOutScheduleChip.isChecked = schedule.flyInFlyOut

            period?.let {
                binding.periodEditText.setText(period)
            }

            binding.periodEditText.makeSoftKeyboardHidden()
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun regionInit(){
        val inputFlow = MutableStateFlow("")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.list_popup_window_item,
            mutableListOf<Areas>()
        )

        with(binding.regionEditText) {
            setAdapter(adapter)
            addTextChangedListener {
                inputFlow.value = it.toString()
            }
            setOnItemClickListener { _, _, position, _ ->
                this.tag = adapter.getItem(position)
                this.setText(adapter.getItem(position)?.name)
                hideSoftKeyboard()
            }
            makeSoftKeyboardHidden()
        }

        lifecycleScope.launch {
            inputFlow
                .debounce(300)
                .filter {
                    it.length > 1
                }
                .mapLatest { prefix ->
                    AreasProvider.areas
                        ?.startWith(prefix,7)
                        ?: listOf()

                }.collect{
                    with(adapter) {
                        clear()
                        addAll(it)
                        notifyDataSetChanged()
                    }
                    binding.regionEditText.requestFocus()
                }
        }

    }



    private fun onSearchButtonPressed() {
       with(binding) {
           object : SearchSettings {
               override var findWords: String? =
                   if (findEditText.text!!.isEmpty()) null
                    else findEditText.text.toString()

               override var excludeWords: String? =
                   if (excludeEditText.text!!.isEmpty()) null
                    else excludeEditText.text.toString()

               override var findIn: FindIn = FindIn(
                   name = nameFieldSearchChip.isChecked,
                   description = descriptionFieldSearchChip.isChecked,
                   companyName = companyNameFieldSearchChip.isChecked
               )

               override var regionId: String? = (regionEditText.tag as? Areas)?.id

               override var experience: String? =
                   when (ExperienceChipGroup.checkedChipId) {
                       R.id.notMatterExperienceChip -> null
                       R.id.noExperienceChip -> EXPERIENCE_NO_EXPERIENCE
                       R.id.between1And3ExperienceChip -> EXPERIENCE_1_3
                       R.id.between3And6ExperienceChip -> EXPERIENCE_3_6
                       R.id.moreThan6ExperienceChip -> EXPERIENCE_MORE_THEN_6
                       else -> null
                   }

               override var schedule: Schedule = Schedule(
                   remote = remoteScheduleChip.isChecked,
                   fullDay = fullDayScheduleChip.isChecked,
                   shift = shiftScheduleChip.isChecked,
                   flexible = flexibleScheduleChip.isChecked,
                   flyInFlyOut = flyInFlyOutScheduleChip.isChecked
               )

               override var period: String? =
                   if (periodEditText.text!!.isEmpty()) null
                    else periodEditText.text.toString()
           }.copyTo(settings)
       }
        saveSettings()
        goForward()
    }

    private fun saveSettings() {
        (settings as? SharedPreferencesSearchSettings)?.saveSettings()
    }

    private fun goForward() {
        findNavController().navigate(R.id.action_searchFragment_to_vacanciesFragment)
    }

    private fun EditText.makeSoftKeyboardHidden() {
    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) hideSoftKeyboard()
    }
}

}
