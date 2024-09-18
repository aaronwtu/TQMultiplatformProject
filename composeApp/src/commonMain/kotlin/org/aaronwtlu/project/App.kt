package org.aaronwtlu.project
import GreetingModule
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.compose.performance.accelerate.AccelerateHeavyScreen
import org.aaronwtlu.project.performance.HeavyScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.dsl.module
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//@Composable
//@Preview
//fun App() {
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
//    }
//}

@Composable
@Preview
fun App(modifier: Modifier = Modifier) {
//    GreetingModule(modifier = modifier)

    AccelerateHeavyScreen(
        viewModel = HeavyScreenViewModel()
    )
}
