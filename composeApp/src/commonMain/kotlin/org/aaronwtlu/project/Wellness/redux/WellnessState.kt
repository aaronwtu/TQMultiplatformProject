package org.aaronwtlu.project.Wellness.redux

import org.aaronwtlu.project.Wellness.WellnessTask

enum class VisibilityFilter {
    SHOW_ALL,
    SHOW_COMPLETED,
    SHOW_ACTIVE
}

data class WellnessState (
    val tasks: List<WellnessTask>  = listOf(),
    val editable: Boolean = false
) {
    val visibleTasks: List<WellnessTask>
        get() = tasks
}