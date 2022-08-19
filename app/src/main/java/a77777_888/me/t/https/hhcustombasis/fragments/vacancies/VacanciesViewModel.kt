package a77777_888.me.t.https.hhcustombasis.fragments.vacancies

import a77777_888.me.t.https.hhcustombasis.model.Singleton
import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class VacanciesViewModel(
    private val sourceProvider: SourceProvider = Singleton.retrofitSourceProvider
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val flowOfVacancies = flowOf(1)
        .flatMapLatest {
            sourceProvider.getPagedVacancies(requireNotNull(Singleton.searchSettings))
        }
        .cachedIn(viewModelScope)

}