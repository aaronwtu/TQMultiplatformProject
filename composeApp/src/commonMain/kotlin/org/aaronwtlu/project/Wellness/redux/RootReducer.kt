package org.aaronwtlu.project.Wellness.redux

fun rootReducer(state: WellnessState, action: Any) = WellnessState(
    tasks = tasksReducer(state.tasks, action),
    editable = editableReducer(state.editable, action)
)
