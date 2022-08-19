package a77777_888.me.t.https.hhcustombasis.utils.logger



interface Logger {

    fun log(tag: String = TAG, message: String)

    fun error(tag: String = TAG, e: Throwable, message: String = "Error: ")

    companion object{
        const val TAG = "HHCB"
    }
}