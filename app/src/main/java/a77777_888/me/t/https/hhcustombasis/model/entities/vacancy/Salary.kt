package a77777_888.me.t.https.hhcustombasis.model.entities.vacancy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Salary(
    val currency: String,
    val from: Int?,
    val gross: Boolean,
    val to: Int?
)