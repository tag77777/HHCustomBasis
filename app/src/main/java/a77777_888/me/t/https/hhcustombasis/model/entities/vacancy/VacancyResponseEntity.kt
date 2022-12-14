package a77777_888.me.t.https.hhcustombasis.model.entities.vacancy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VacancyResponseEntity(
    val accept_handicapped: Boolean,
    val accept_incomplete_resumes: Boolean,
    val accept_kids: Boolean,
    val accept_temporary: Boolean?,
    val address: Any,
    val allow_messages: Boolean,
    val alternate_url: String,
    val apply_alternate_url: String,
    val archived: Boolean,
    val area: Area,
    val billing_type: BillingType,
    val branded_description: Any,
    val code: Any,
    val contacts: Any,
    val created_at: String,
    val department: Any,
    val description: String,
    val driver_license_types: List<Any>,
    val employer: Employer,
    val employment: Employment,
    val experience: Experience,
    val has_test: Boolean,
    val hidden: Boolean,
    val id: String,
    val initial_created_at: String,
    val insider_interview: Any,
    val key_skills: List<KeySkill>,
    val languages: List<Any>,
    val name: String,
    val negotiations_url: Any,
    val premium: Boolean,
    val professional_roles: List<ProfessionalRole>,
    val published_at: String,
    val quick_responses_allowed: Boolean,
    val relations: List<Any>,
    val response_letter_required: Boolean,
    val response_url: Any,
    val salary: Salary?,
    val schedule: Schedule,
    val specializations: List<Specialization>,
    val suitable_resumes_url: Any,
    val test: Any,
    val type: Type,
    val vacancy_constructor_template: Any,
    val working_days: List<Any>,
    val working_time_intervals: List<WorkingTimeInterval>,
    val working_time_modes: List<WorkingTimeMode>
)