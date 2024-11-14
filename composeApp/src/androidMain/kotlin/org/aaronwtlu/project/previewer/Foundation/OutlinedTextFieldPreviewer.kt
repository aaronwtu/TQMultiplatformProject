package org.aaronwtlu.project.previewer.Foundation

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.leanback.widget.Util
import kotlinx.parcelize.Parcelize

@Composable
fun HelloContent() {
    /// remember 记录工具
    // TODO: 如何理解这两个 remember
//    var name by remember { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }

    HelloInputField(name) { name = it }
}

@Composable
fun HelloInputField(value: String, onValueChanged: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (value.isNotEmpty()) {
            Text(
                text = "Hello! $value",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            label = { Text("Value") }
        )
    }
}


@Parcelize
data class Person(var name: String, var age: Int) : Parcelable

@Composable
fun UserInfoField(modifier: Modifier) {
    // TODO: 为什么使用 remember 无效？
    val personState = rememberSaveable {
        mutableStateOf(Person("Aaron", 18))
    }
    UserInfoInputFieldView(modifier, personState)
}

@Composable
fun UserInfoInputFieldView(modifier: Modifier, personState: MutableState<Person>) {
    /// 通过 by 将 mutable 中的 Person 取出来
    var person by personState

    Column(
        modifier = modifier
    ) {
        TextField(
            value = person.name,
            onValueChange = {
                person = person.copy(name = it)
            }
        )
        TextField(
            value = "${person.age}",
            onValueChange = {
                person = person.copy(age = it.toInt())
            },
            label = { Text("Enter a number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

