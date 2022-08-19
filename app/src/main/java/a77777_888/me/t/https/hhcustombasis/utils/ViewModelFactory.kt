package a77777_888.me.t.https.hhcustombasis.utils

typealias ViewModelCreator<VM> = () -> VM

//class ViewModelFactory<VM: ViewModel>(
//    private val viewModelCreator: ViewModelCreator<VM>
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return viewModelCreator() as T
//    }
//}
//
//inline fun <reified VM: ViewModel> Fragment.viewModelCreator(noinline creator: ViewModelCreator<VM>)
//            : Lazy<VM> {
//    return viewModels { ViewModelFactory(creator) }
//}