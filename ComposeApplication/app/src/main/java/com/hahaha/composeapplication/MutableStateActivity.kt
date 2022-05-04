package com.hahaha.composeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MutableStateActivity : ComponentActivity() {
    // mutableStateOf 实现了 MutableState 接口
    // 这个name就是一个MutableState类型的对象，这样这个name就可以被订阅了 #1
    private val name = mutableStateOf("zyp is niu")

    // 通过by函数可以省略.value这一步
    private var nameUserBy by mutableStateOf("zyp niu niu ")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                showName()
                updateListData()
            }
        }

        // 3s 后，修改name.value后，界面刷新Text的值
        lifecycleScope.launch {
            delay(3000)
            name.value = "zyp is niu 123"
            nameUserBy = " zyp is niu 123 4 by  "
        }
    }

    // name.value就相当于订阅了MutableState类型的对象，监听name.value的变化 #1
    @Composable
    private fun showName() {
        Text(text = name.value)
        Text(text = nameUserBy)
    }

    // 通过mutableStateListOf 和 mutableStateMapOf
    // 创建出来的集合对象才可以在赋值的时候，被Compose监听，然后修改值的时候就会被页面刷新
    val nums = mutableStateListOf<Int>(1, 2, 3)
    val numsMap = mutableStateMapOf(1 to "One", 2 to "Tow")

    @Composable
    private fun updateListData() {
        Column() {
            Button(onClick = {
                nums.add(nums.last() + 1)
            }) {
                Text(text = "click List")
            }
            nums.forEach {
                Text(text = "数字是：$it")
            }

            Button(onClick = {
                numsMap[numsMap.size + 1] = ("gggggggg")
            }, modifier = Modifier.apply {
                padding(20.dp)
            }) {
                Text(text = "click Map")
            }
            numsMap.forEach {
                Text(text = "key是：${it.key}  value 是 ${it.value} ")
            }
        }
    }
}


