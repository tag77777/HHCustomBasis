package a77777_888.me.t.https.hhcustombasis.model.entities.vacancy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Specialization(
    val id: String,
    val name: String,
    val profarea_id: String,
    val profarea_name: String
)