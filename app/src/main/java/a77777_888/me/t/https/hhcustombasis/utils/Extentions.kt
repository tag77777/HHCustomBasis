package a77777_888.me.t.https.hhcustombasis.utils

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
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

fun AppCompatActivity.setToolBarColor(@ColorRes color: Int) {
    val colorInt = resources.getColor(color)
    val colorDrawable = ColorDrawable(colorInt)
    supportActionBar?.setBackgroundDrawable(colorDrawable)
}

fun Fragment.hideSoftKeyboard() = ViewCompat.getWindowInsetsController(requireView())
    ?.hide(WindowInsetsCompat.Type.ime())

