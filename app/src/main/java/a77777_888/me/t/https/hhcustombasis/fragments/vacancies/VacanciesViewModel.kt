package a77777_888.me.t.https.hhcustombasis.fragments.vacancies

import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class VacanciesViewModel @Inject constructor(
    private val sourceProvider: SourceProvider,
    private val searchSettings: SearchSettings
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val flowOfVacancies = flowOf(1)
        .flatMapLatest {
            sourceProvider.getPagedVacancies(searchSettings)
        }
        .cachedIn(viewModelScope)

}