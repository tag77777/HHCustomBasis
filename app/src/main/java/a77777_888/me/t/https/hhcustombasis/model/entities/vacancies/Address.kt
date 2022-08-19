package a77777_888.me.t.https.hhcustombasis.model.entities.vacancies

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    val building: String,
    val city: String,
    val description: Any,
    val id: String,
    val lat: Double,
    val lng: Double,
    val metro: Any,
    val metro_stations: List<Any>,
    val raw: String,
    val street: String
)