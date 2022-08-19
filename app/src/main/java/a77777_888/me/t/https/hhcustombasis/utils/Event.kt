//package a77777_888.me.t.https.hhcustombasis.utils
//
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//class Event<T> (valueParam: T) {
//    private var value: T? = valueParam
//
//    fun get(): T? = if (value == null) null
//                    else value.also { value = null }
//}
//
//fun <T> MutableLiveData<T>.share(): LiveData<T> = this
//
//typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
//typealias LiveEvent<T> = LiveData<Event<T>>
//typealias EventListener<T> = (T) -> Unit
//
//fun <T> MutableLiveEvent<T>.publishEvent(valueParam: T) {
//    this.value = Event(valueParam)
//}
//
//fun <T> LiveEvent<T>.observeEvent(lifecycleOwner: LifecycleOwner, listener: EventListener<T>) {
//    this.observe(lifecycleOwner) {
//        it?.get()?.let { value ->
//            listener(value)
//        }
//    }
//}
//
//typealias MutableUnitLiveEvent = MutableLiveEvent<Unit>
//typealias UnitLiveEvent = LiveEvent<Unit>
//typealias UnitEventListener = () -> Unit
//
//fun MutableUnitLiveEvent.publishEvent() = publishEvent(Unit)
//
//fun UnitLiveEvent.observeEvent(lifecycleOwner: LifecycleOwner, listener: UnitEventListener) {
//    observeEvent(lifecycleOwner) { _ ->
//        listener()
//    }
//}