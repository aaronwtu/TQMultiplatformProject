import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.schedule.android.GreetingTheme

@Composable
fun GreetingModule(modifier: Modifier) {
    //    var shouldShowOnboarding by remember { mutableStateOf(true) }
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    GreetingTheme {
        Surface(
            modifier = modifier
        ) {
            if (shouldShowOnboarding) { // Where does this come from?
                OnboardingScreen(onContinueClicked = {
                    println("clicked")
                    shouldShowOnboarding = false
                })
            } else {
                Column {
                    GreetingEntrance()
                    Button(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .height(40.dp),
                        onClick = {
                            shouldShowOnboarding = true
                        }) {
                        Text(text = "Back")
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue>")
        }
    }
}
