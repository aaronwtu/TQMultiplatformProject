
import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.lifecycle.AppLifecycleObserver

/*
class MainActivity : ComponentActivity() {
    private val lifecycleObserver = AppLifecycleObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(lifecycleObserver)
        setContent {
            ComposeLifecyclePreviewer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleObserver)
    }
}
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeLifecyclePreviewer() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle
    val lifecycleObserver = remember { AppLifecycleObserver() }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> Klog.d("MyApp", "App Created")
                Lifecycle.Event.ON_START -> Klog.d("MyApp", "App Started")
                Lifecycle.Event.ON_RESUME -> Klog.d("MyApp", "App Resumed")
                Lifecycle.Event.ON_PAUSE -> Klog.d("MyApp", "App Paused")
                Lifecycle.Event.ON_STOP -> Klog.d("MyApp", "App Stopped")
                Lifecycle.Event.ON_DESTROY -> Klog.d("MyApp", "App Destroyed")
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    // Your Compose UI content
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lifecycle Aware Compose") }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text("Icon")
            }
        }
    ) {
        Text("Hello, World! $it")
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeLifecyclePreviewerExample() {
    ComposeLifecyclePreviewer()
}