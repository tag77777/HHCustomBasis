package a77777_888.me.t.https.hhcustombasis

import com.google.gson.internal.bind.util.ISO8601Utils
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RunWith(JUnit4::class)
class DetailsTest {


    @Test
    fun dataTest() {
//        val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm")
        val timeStamp = "2022-08-21T09:52:13+0300"

        val localDateTime = LocalDateTime.parse(timeStamp.dropLast(5))
        var myData = localDateTime.format(DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm"))

        println("\n$timeStamp --- $myData\n")

        val data = ISO8601Utils.parse(timeStamp, ParsePosition(0))
        myData = SimpleDateFormat("d MMMM yyyy, HH:mm").format(data)

        println("\n$timeStamp --- $myData\n")

    }
}