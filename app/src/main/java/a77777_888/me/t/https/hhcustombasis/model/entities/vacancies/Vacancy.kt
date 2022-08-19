package a77777_888.me.t.https.hhcustombasis.model.entities.vacancies

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Vacancy(
    val accept_temporary: Boolean?,
    val address: Address?,
    val adv_response_url: String,
    val alternate_url: String, // Ссылка на представление вакансии на сайте
    val apply_alternate_url: String,
    val archived: Boolean,
    val area: Area,
    val contacts: Any?,
    val created_at: String,
    val department: Any?,
    val employer: Employer,
    val has_test: Boolean,
    val id: String,
    val insider_interview: Any?,
    val name: String,
    val premium: Boolean,
    val published_at: String,
    val relations: List<Any>,
    val response_letter_required: Boolean,
    val response_url: String?,
    val salary: Salary?,
    val schedule: Schedule,
    val snippet: Snippet,
    val sort_point_distance: Float?,
    val type: Type,
    val url: String,
    val working_days: List<Any>,
    val working_time_intervals: List<Any>,
    val working_time_modes: List<Any>
)