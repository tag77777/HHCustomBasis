package a77777_888.me.t.https.hhcustombasis.ui.vacancies

import a77777_888.me.t.https.hhcustombasis.R
import a77777_888.me.t.https.hhcustombasis.databinding.VacanciesFragmentBinding
import a77777_888.me.t.https.hhcustombasis.ui.vacancies.adapters.DefaultLoadStateAdapter
import a77777_888.me.t.https.hhcustombasis.ui.vacancies.adapters.DefaultLoadStateAdapterNavigator
import a77777_888.me.t.https.hhcustombasis.ui.vacancies.adapters.VacanciesAdapter
import a77777_888.me.t.https.hhcustombasis.ui.vacancies.adapters.VacanciesAdapterNavigator
import a77777_888.me.t.https.hhcustombasis.ui.vacancy.VacancyFragment
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class VacanciesFragment
    : Fragment(R.layout.vacancies_fragment),
    VacanciesAdapterNavigator, DefaultLoadStateAdapterNavigator {

    private lateinit var binding: VacanciesFragmentBinding
    private val viewModel by viewModels<VacanciesViewModel>()
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    private val vacanciesAdapter = VacanciesAdapter(this)

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = VacanciesFragmentBinding.bind(view)

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(binding.loadStateView, this)

        setupVacanciesList()
        observeVacancies(vacanciesAdapter)
        observeLoadState(vacanciesAdapter)

    }

    private fun setupVacanciesList() {
        binding.recyclerView.adapter = vacanciesAdapter.withLoadStateFooter(
            footer = DefaultLoadStateAdapter(this)
        )
    }

    private fun observeVacancies(adapter: VacanciesAdapter) {
       lifecycleScope.launch {
            viewModel.flowOfVacancies
                .collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    @FlowPreview
    private fun observeLoadState(adapter: VacanciesAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest {
                mainLoadStateHolder.bind(it.refresh)
            }
        }
    }

    override fun goToVacancy(vacancyId: String) {
        findNavController().navigate(
            R.id.action_vacanciesFragment_to_vacancyFragment,
            bundleOf(VacancyFragment.ID to vacancyId)
        )
    }

    override fun tryAgain() {
        vacanciesAdapter.retry()
    }

    override fun goToSearch() {
        findNavController().popBackStack()
    }
}