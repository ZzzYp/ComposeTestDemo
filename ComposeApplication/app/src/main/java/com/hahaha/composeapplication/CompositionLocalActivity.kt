package com.hahaha.composeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * CompositionLocal
 *
 *
 */

class CompositionLocalActivity : ComponentActivity() {
    val themeBackgroundColor by mutableStateOf(Color.Blue)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // LocalName provides "zyp" == LocalName.provides("zyp")
            /**
             * 使用compositionLocalOf和CompositionLocalProvider配合
             * 可以做到在function1()内部直接使用LocalName.current获取我们定义的name值
             */
            CompositionLocalProvider(
                LocalName provides "zyp",
                LocalAge provides "永远18"
            ) {
                Column {
                    //function1()
                    CompositionLocalProvider(LocalBackground provides Color.Cyan) { //#2
                        function1()
                        CompositionLocalProvider(LocalBackground provides themeBackgroundColor) {
                            function1() // #1
                        }
                    }
                }
            }
        }
    }
}


val LocalName = compositionLocalOf<String> { error("请提供默认值") }

val LocalAge = staticCompositionLocalOf<String> { error("请提供默认值") }

// 会追踪变量的使用，即compose会标记使用过的地方，当值发生改变的时候，会触发recompose，重组使用过的地方,如上面例子的#1位置，会发生重组
val LocalBackground = compositionLocalOf<Color> { error("请提供默认值") }
// 不会追踪变量的使用，在值发生改变的时候，会触发全量recompose，如上面例子的#2位置内部的代码，会发生重组
val LocalBackgroundStatic = staticCompositionLocalOf<Color> { error("请提供默认值") }

@Composable
private fun function1() {
    //LocalName.current 只能在@Composable函数中使用
    Text(
        text = LocalName.current.plus(" -> ${LocalAge.current}"),
        modifier = Modifier
            .padding(30.dp)
            .background(LocalBackground.current),
        fontSize = 20.sp,
        color = Color.Blue,
        )
}



