package a77777_888.me.t.https.hhcustombasis.model.entities.vacancies

//@JsonClass(generateAdapter = true)
data class VacanciesResponseEntity(
    val alternate_url: String,
    val arguments: Any,
    val clusters: Any,
    val found: Int,
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val per_page: Int
)