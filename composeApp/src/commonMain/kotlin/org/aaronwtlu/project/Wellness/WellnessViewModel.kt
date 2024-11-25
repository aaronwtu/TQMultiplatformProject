package org.aaronwtlu.project.Wellness

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import kotlinx.serialization.Serializable
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.Wellness.redux.AddTask
import org.aaronwtlu.project.Wellness.redux.Init
import org.aaronwtlu.project.Wellness.redux.RemoteTask
import org.aaronwtlu.project.Wellness.redux.WellnessState
import org.aaronwtlu.project.Wellness.redux.rootReducer
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscription
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.middleware
import org.reduxkotlin.threadsafe.createThreadSafeStore
import org.reduxkotlin.thunk.Thunk
import org.reduxkotlin.thunk.ThunkMiddleware

//import kotlinx.parcelize.Parcelize


data class WellnessTask(
    val id: Int,
    val label: String,
    private var initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}

fun WellnessTask.toMap(): Map<String, String> {
    return mapOf(
        "id" to this.id.toString(),
        "label" to this.label,
        "checked" to this.checked.toString()
    )
}

private fun Map<String, String>.toTask(): WellnessTask {
    return WellnessTask(
        this.getValue("id").toInt(),
        this.getValue("label"),
        this.getValue("checked").toBoolean()
    )
}

val WellnessTaskSaver = listSaver<WellnessTask, Any>(
    save = { listOf(it.id, it.label) },
    restore = { WellnessTask(it[0] as Int, it[1] as String, it[2] as Boolean) }
)

/**
 * Logs all actions and states after they are dispatched.
 */
val loggerMiddleware222 = middleware<WellnessState> { store, next, action ->
    val result = next(action)
    Klog.i("DISPATCH action: ${action::class.simpleName}: $action")
    Klog.i("next state: ${store.state}")
    result
}

/**
 * Same functionality as above, but declared using a function
 */
fun loggerMiddleware(store: Store<WellnessState>) = { next: Dispatcher ->
    { action: Any ->
        val result = next(action)
        Klog.i("DISPATCH action: ${action::class.simpleName}: $action")
        Klog.i("next state: ${store.state}")
        result
    }
}

/**
 * Sends crash reports as state is updated and listeners are notified.
 */
val crashReporter = middleware<WellnessState> { store, next, action ->
    try {
        return@middleware next(action)
    } catch (e: Exception) {
        // report to crashlytics, etc
        throw e
    }
}

/**
 * From redux-kotlin-thunk.  This how thunks are executed.
 */
fun createThunkMiddleware(extraArgument: Any? = null): ThunkMiddleware<WellnessState> = { store ->
    { next: Dispatcher ->
        { action: Any ->
            if (action is Function<*>) {
                try {
                    (action as Thunk<*>)(store.dispatch, store.getState, extraArgument)
                } catch (e: Exception) {
                    throw IllegalArgumentException()
                }
            } else {
                next(action)
            }
        }
    }
}

fun createWellnessStore(): Store<WellnessState> {
    return createThreadSafeStore(
        ::rootReducer,
        WellnessState(),
        applyMiddleware(
            createThunkMiddleware(),
            ::loggerMiddleware,
            crashReporter
        )
    )
}


class WellnessViewModel : ViewModel() {
    /// 可监听
    var tasks by mutableStateOf(arrayListOf<WellnessTask>())
    var editAble by mutableStateOf(false)

    val store = createWellnessStore()
    private var storeSubscription: StoreSubscription

    init {
        storeSubscription = store.subscribe {
            editAble = store.state.editable
            val elements = store.state.tasks
            tasks = arrayListOf<WellnessTask>().apply { addAll(elements) }
        }
        store.dispatch(Init(getWellnessTasks()))
    }


//    fun remove(task: WellnessTask) {
//        Klog.i(this@WellnessViewModel.hashCode().toString() + " ${task.id}")
//        _tasks.remove(task)
//    }
//
//    fun add(task: WellnessTask) {
//        _tasks.add(0, task)
//    }
}

val WellnessViewModelSaver = Saver<WellnessViewModel, List<Map<String, String>>>(save = {
    val save = it.tasks.map { it.toMap() }
    Klog.i("save: $save")
    return@Saver save
}, restore = {
    Klog.i("restore: $it")
    val model = WellnessViewModel()
    model.store.dispatch(Init(it.map { it.toTask() }))
    model
})

fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i", false) }