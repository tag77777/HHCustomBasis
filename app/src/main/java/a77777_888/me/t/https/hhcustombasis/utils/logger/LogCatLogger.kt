package a77777_888.me.t.https.hhcustombasis.utils.logger

import android.util.Log

object LogCatLogger : Logger {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun error(tag: String, e: Throwable, message: String) {
        Log.e(tag, message, e)
    }
}