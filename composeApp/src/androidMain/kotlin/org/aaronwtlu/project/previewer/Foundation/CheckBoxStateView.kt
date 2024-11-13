package org.aaronwtlu.project.previewer.Foundation

import android.os.Parcelable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckBoxState constructor(
    val isSelected: Boolean = false,
    val name: String = "Checkbox State"
) : Parcelable

@Composable
fun CheckBoxStateView() {
    var checkBoxState by rememberSaveable {
        mutableStateOf(CheckBoxState())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked =checkBoxState.isSelected , onCheckedChange = {
            checkBoxState =checkBoxState.copy(isSelected = it)
        })
        Text(text = checkBoxState.name)
    }
}
