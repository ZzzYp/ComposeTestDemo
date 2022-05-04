package com.hahaha.composeapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*

/**
 *
 *
 *
 */


class NoStatusAndStatusUpAndOneWayDataFlowActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                var name2 = "HHHHHHa"
                // function1 Text()的文字状态在function1内部
                function1()
                // function2 Text()的文字状态在function2外部，所谓状态提升就是把文字状态从function内部提升到外部
                function2(name2)
                function3() {
                    Log.e("zyp", "  value Change $it  ")
                }
                function4()
            }
        }
    }

    @Composable
    private fun function1() {
        val name = "Heeeee"
        Text(text = name)
    }

    @Composable
    private fun function2(name2: String) {
        Text(text = name2)
    }

    @Composable
    private fun function3(onValueChange: (value: String) -> Unit) {
        // TextField 是 material层的
        // 如果想要自定义文本输入框，可以用BasicTextField
        // Compose的TextField不会自动更新value，所以我们需要在onValueChange中主动通过name = it更新value
        // 而name是可变的变量，所以要使用mutableStateOf和remember
        // 这样设计有个好处，就是可以在name = it的地方过滤输入的内容
        var name by remember { mutableStateOf("") }
        TextField(value = name, onValueChange = {
            name = it
            onValueChange.invoke(it)
        })
    }

    @Composable
    private fun function4(name: String = "Temp") {

    }
}


