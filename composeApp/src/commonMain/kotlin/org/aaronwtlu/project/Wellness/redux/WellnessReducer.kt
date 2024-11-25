package org.aaronwtlu.project.Wellness.redux

import org.aaronwtlu.project.Wellness.WellnessTask

fun tasksReducer(tasks: List<WellnessTask>, action: Any) =
    when (action) {
        is AddTask -> tasks + action.task
        is RemoteTask -> tasks.filter { it.id == action.id }
        is ToggleTask -> tasks.mapIndexed { index, task ->
            if (task.id == action.task.id) {
                task.checked = !task.checked
            }
            task
        }
        is Init -> action.tasks
        else -> tasks
    }

fun editableReducer(editable: Boolean, action: Any) =
    when (action) {
        is ToggleEdit -> !editable
        else -> editable
    }