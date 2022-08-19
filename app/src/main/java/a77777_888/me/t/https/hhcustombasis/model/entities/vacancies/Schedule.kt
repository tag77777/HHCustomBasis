package a77777_888.me.t.https.hhcustombasis.model.entities.vacancies

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Schedule(
    val id: String,
    val name: String
)