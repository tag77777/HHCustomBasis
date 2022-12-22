package a77777_888.me.t.https.hhcustombasis.model

import a77777_888.me.t.https.hhcustombasis.utils.logger.LogCatLogger
import a77777_888.me.t.https.hhcustombasis.utils.logger.Logger

sealed class Result<T> {

    fun getValueOrNull(): T? =
         if (this is SuccessResult<T>) this.value
        else null

    fun isFinished() = this is SuccessResult<T> || this is ErrorResult<T>

}

class EmptyResult<T> : Result<T>()

class PendingResult<T> : Result<T>()

class SuccessResult<T>(val value: T) : Result<T>()

class ErrorResult<T>(messageParam:String = "Error: ",
                     val error: Throwable? = null,
                     logger: Logger = LogCatLogger
) : Result<T>() {
    var message: String = if (error == null) {
        logger.log(message = messageParam)
        messageParam
    } else {
        logger.error(
            message = messageParam,
            e = error
        )
        messageParam
    }

}
