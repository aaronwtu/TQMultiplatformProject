package org.aaronwtlu.project.Previewer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.Wellness.WellnessViewModel
import org.aaronwtlu.project.Wellness.WellnessViewModelSaver


private val DarkColorScheme = darkColorScheme(
    /// 自定义
)
private val LightColorScheme = lightColorScheme(
    /// 自定义
)

val myTypography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 96.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    )
)

private val darkTheme: Boolean = true


data class Elevations(val card: Dp = 0.dp, val default: Dp = 0.dp)
val LocalElevations = compositionLocalOf { Elevations() }

@Composable
fun CompositionLocalProviderPreviewer(
    modifier: Modifier = Modifier
) {
    val elevations = Elevations(card = 1.dp, default = 1.dp)

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = myTypography
    ) {
        CompositionLocalProvider(LocalElevations provides elevations) {
            Surface {
                Column {
                    Text("Uses Surface's provided content color")
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
                        /// color
                        Text("Primary color provided by LocalContentColor")
                        Text("This Text also uses primary as textColor")

                        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
                            ErrorElementText()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorElementText() {
    Text("This Text uses the error color now => ${LocalElevations.current}")
}
