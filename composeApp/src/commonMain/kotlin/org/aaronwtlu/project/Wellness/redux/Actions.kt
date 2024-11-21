package org.aaronwtlu.project.Wellness.redux

import org.aaronwtlu.project.Wellness.WellnessTask

data class Init(val tasks: List<WellnessTask>)
data class AddTask(val task: WellnessTask)
data class RemoteTask(val id: Int)

data class ToggleTask(val index: Int)
data class ToggleEdit(val editable: Boolean)
