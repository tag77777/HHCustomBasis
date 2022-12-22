package a77777_888.me.t.https.hhcustombasis.ui.base

import a77777_888.me.t.https.hhcustombasis.model.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val resultFlow = MutableStateFlow<Result<out Any>>(EmptyResult())

    fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: ParseBackendResponseException) {
                resultFlow.value = ErrorResult("Ошибка парсинга", error = e)
            }
            catch (e: ConnectionException) {
                resultFlow.value = ErrorResult("Ошибка парсинга", error = e)
            }
            catch (e: BackendException) {
                resultFlow.value =
                    ErrorResult("Ошибка сервера:\nКод ${e.code}\n${e.message}", error = e)
            }
            catch (e: Exception) {
                resultFlow.value = ErrorResult("Внутренняя ошибка", error = e)
            }
        }
    }

}