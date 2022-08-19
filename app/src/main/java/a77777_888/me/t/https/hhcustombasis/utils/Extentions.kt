package a77777_888.me.t.https.hhcustombasis.utils

import android.annotation.SuppressLint
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.toPatternDateStringFromISO8601String(
    pattern: String = "d MMMM yyyy, HH:mm"
): String =
    try {
        val data = ISO8601Utils.parse(this, ParsePosition(0))
        SimpleDateFormat(pattern).format(data)
    } catch (e: Exception){ "" }
        // required API level 26
//         LocalDateTime.parse(this.dropLast(5))
//            .format(DateTimeFormatter.ofPattern(pattern))
