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

//import kotlinx.parcelize.Parcelize


class WellnessTask(val id: Int, val label: String, private var initialChecked: Boolean = false) {
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

class WellnessViewModel(
    private var _tasks: SnapshotStateList<WellnessTask> = getWellnessTasks().toMutableStateList()
) : ViewModel() {
    val tasks: SnapshotStateList<WellnessTask>
        get() = _tasks

    fun remove(task: WellnessTask) {
        Klog.i(this@WellnessViewModel.hashCode().toString() + " ${task.id}")
        _tasks.remove(task)
    }
}

val WellnessViewModelSaver = Saver<WellnessViewModel, List<Map<String, String>>>(save = {
    val save = it.tasks.map { it.toMap() }
    Klog.i("save: $save")
    return@Saver save
}, restore = {
    Klog.i("restore: $it")
    WellnessViewModel(
        _tasks = it.map { it.toTask() }.toMutableStateList()
    )
})

fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i", false) }