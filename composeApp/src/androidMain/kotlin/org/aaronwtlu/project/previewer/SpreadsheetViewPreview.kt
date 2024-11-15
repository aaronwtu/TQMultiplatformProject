package org.aaronwtlu.project.previewer

import android.widget.ScrollView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import org.aaronwtlu.project.imageviewer.view.ScrollableColumn
import org.aaronwtlu.project.previewer.Foundation.CheckBoxStateView
import org.aaronwtlu.project.previewer.Foundation.HelloContent
import org.aaronwtlu.project.previewer.Foundation.TextPreview
import org.aaronwtlu.project.previewer.Foundation.UserInfoField
import tqmultiplatformproject.composeapp.generated.resources.Res


@Composable
fun SpreadsheetViewPreview() {

    ScrollableColumn(
        modifier = Modifier
            .padding()
    ) {
        (0..<100).forEach {
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    "$it",
                    style = TextStyle(fontSize = 30.sp)
                )
            }
        }
    }
}

@Composable
fun MyComponent2() {
    val items = (0..10000).toList()
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = items.chunked(100)) { rowItems ->
            LazyColumn(
                modifier = Modifier.width(200.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = rowItems) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.LightGray)
                    ) {
                        Text(
                            text = "Item $item",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MyComponent() {
    val items = (0..10000).toList()

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = items.chunked(100)) { rowItems ->
            LazyColumn(
                modifier = Modifier.width(200.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = rowItems) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.LightGray)
                    ) {
                        Text(
                            text = "Item $item",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WellnessScreenPreviewExample() {
    MyComponent2()
//    SpreadsheetViewPreview()
}