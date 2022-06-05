package com.hahaha.composeapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.flow

/**
 * ComposeToNative
 *
 *
 */

class ComposeToNativeActivity : ComponentActivity() {

    val namLive = MutableLiveData<String>("haha")
    val nameFlow = flow<String> {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_native)
        val composeView = findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            Column(modifier = Modifier.padding(30.dp)) {
                Text(text = "这是一个compose text", fontSize = 25.sp)
                AndroidView(factory = {
                    // 创建初始化放在这儿
                    TextView(this@ComposeToNativeActivity).apply {
                        text = "这是一个Native TextView"
                        textSize = 25f
                    }
                }) {
                    // 这儿更新native的view数据，在recompose过程中更新
                }
                // livedata转为mutableState
                val name by namLive.observeAsState()
                Text(text = name ?: "", fontSize = 25.sp)
                // flow转为mutableState
                val nameFl by nameFlow.collectAsState(initial = "heihei")
                Text(text = nameFl ?: "", fontSize = 25.sp)
                SideEffect {

                }
            }
        }

    }
}




