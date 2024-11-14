package org.aaronwtlu.project.previewer.Layout

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.*


// TODO: 了解 Lint 机制
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.myBackground(color: Color) = padding(16.dp)
    .clip(RoundedCornerShape(8.dp))
    .background(color)

@Composable
fun Modifier.myBackground(): Modifier {
    val color = LocalContentColor.current
    return this then Modifier.background(color.copy(alpha = 0.5f))
}


// https://developer.android.com/develop/ui/compose/custom-modifiers?hl=zh-cn
@Composable
fun Modifier.fade(enable: Boolean): Modifier {
    val alpha by animateFloatAsState(if (enable) 0.5f else 1.0f, label = "")
    // TODO:  创建自定义修饰符时，请勿破坏修饰符链。您必须始终引用 this，否则之前添加的任何修饰符都会被舍弃。
    //  您可以像前面的示例中一样使用 this then Modifier
    //  也可以隐式使用 return graphicsLayer { this.alpha = alpha }。
    return this then Modifier.graphicsLayer { this.alpha = alpha }
}

// TODO: ? 在工厂方法里面调用了其他 compose 元素？
@Composable
fun Modifier.fadedBackground(): Modifier {
    val color = LocalContentColor.current
    return this then Modifier.background(color.copy(alpha = 0.5f))
}

// Modifier.Node
private class CircleNode(var color: Color) : DrawModifierNode, Modifier.Node() {
    override fun ContentDrawScope.draw() {
        drawCircle(color)
    }
}

// ModifierNodeElement
private data class CircleElement(val color: Color) : ModifierNodeElement<CircleNode>() {
    override fun create() = CircleNode(color)
    override fun update(node: CircleNode) {
        node.color = color
    }
}
// Modifier factory
fun Modifier.circle(color: Color) = this then CircleElement(color)
