package a77777_888.me.t.https.hhcustombasis.source

import a77777_888.me.t.https.hhcustombasis.model.entities.vacancies.Vacancy
import androidx.paging.PagingSource
import androidx.paging.PagingState

typealias VacancyPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Vacancy>

class VacanciesPagingSource(
    private val loader: VacancyPageLoader,
    private val pageSize: Int
) : PagingSource<Int, Vacancy>() {

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        val pageIndex = params.key ?: 0

        return try {
            val vacancies = loader(pageIndex, params.loadSize)

            if (pageIndex == 0 && vacancies.isEmpty())
                throw EmptyListException()

            return LoadResult.Page(
                data = vacancies,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (vacancies.size == params.loadSize)
                                pageIndex + (params.loadSize / pageSize)
                          else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

class EmptyListException : Exception()