package com.hahaha.composeapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 *
 *
 *
 */


class DerivedStateOfAndRememberActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollState = rememberScrollState()
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                // 这两个方法，在UI效果上是一致的
                derivedStateOfFun()
                rememberFun()
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                remember4List()
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                remember4List2()
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                derived4List()
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                var name by remember { mutableStateOf("zyp") } // #1
                remember4Parameter(name) {
                    name = "gary"
                }
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                derived4Parameter(name) {
                    name = "gary"
                }
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                var nameStateOf = remember { mutableStateOf("zyp") } // #2
                derived4StateOfParameter(nameStateOf) {
                    nameStateOf.value = "gary"
                }
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                val nameList = remember { mutableStateListOf("zyp", " gary ") }
                rememberListParameter(nameList) {
                    nameList.add("szx")
                }
                Text(text = "-------------------------------", modifier = Modifier.padding(10.dp))
                derivedListParameter(nameList) {
                    nameList.add("szx")
                }
            }
        }
    }

    @Composable
    private fun derivedStateOfFun() {
        var name by remember { mutableStateOf("zyp is niu") }
        val convertName by remember { derivedStateOf { name.uppercase() } }
        // 初始显示的是ZYP IS NIU，点击以后显示的是NIU IS ZYP
        // 因为name改变以后，被derivedStateOf包裹的代码块监听到name发生了改变，所以会重新执行derivedStateOf包裹的代码块
        // 就会导致convertName发生变化，从ZYP IS NIU -> NIU IS ZYP
        Row(Modifier.padding(20.dp)) {
            Text(text = convertName, Modifier.clickable { name = "niu is zyp" })
        }
    }

    @Composable
    private fun rememberFun() {
        var name by remember { mutableStateOf("zyp is niu") }
        val convertName = remember(name) { name.uppercase() }
        // 初始显示的是ZYP IS NIU，点击以后显示的是NIU IS ZYP
        Row(Modifier.padding(20.dp)) {
            Text(text = convertName, Modifier.clickable { name = "niu is zyp" })
        }
    }


    @Composable
    private fun remember4List() {
        val list = remember { mutableStateListOf<String>(" zyp ", " szx ") }
        list.forEach {
            // 触发了下面的点击事件以后，log打印的内容为 zyp, szx, gary,但是页面并没有显示gary，这是为什么呢？
            Log.e("zyp", "  list $it ")
        }
        /**
         * 当我们触发了下面的点击事件，list add了一个内容进去，这个时候，由于是通过mutableStateListOf定义的list，所以会触发recompose
         * 那么remember4List代码块内的代码会重新执行一遍
         * 当走到convertName = remember...的时候，由于key1是list，是一个对象，
         * 而recompose 在执行到remember()代码的时候，会进行比较，看数据内容是否发生了改变，如果发生了改变，才会执行remember()代码块内的代码
         * 但是在这个地方，这种写法，使用的是java的equals和kotlin的==来比较的，所以recompose的时候，其实是针对同一个list对象进行了equals/==的比较
         * 那么比较的结果当然是true了，即是同一个对象，那么remember()代码块内的代码就不会执行，新增的gary这个内容也就不会显示在屏幕上
         */
        val convertName = remember(key1 = list) {
            list.map { it.uppercase() }
        }

        Column() {
            convertName.forEach {
                Text(text = it, Modifier.clickable {
                    list.add(" gary ")
                })
            }
        }
    }

    @Composable
    private fun remember4List2() {
        /**
         * 针对remember4List中出现的问题，这种写法是避免问题的一种解决方法，但是在add的时候，每次都会创建一个新的list
         * 因为这种解法的本质就是规避remember(key1 = list) 在recompose时，每次都比较自己的问题
         */
        var list by remember { mutableStateOf(listOf("zyp", "gary ")) }
        val convertName = remember(key1 = list) {
            list.map { it.uppercase() }
        }
        Column() {
            convertName.forEach {
                Text(text = it, modifier = Modifier.clickable {
                    list = list.toMutableList().apply {
                        add("szx")
                    }
                })
            }
        }
    }

    @Composable
    private fun derived4List() {
        /**
         * remember4List 中碰到的问题，加个derivedStateOf { }就可以解决了,因为derivedStateOf包括的代码块中，只要内容发生了改变，就会重新执行
         */
        val list = remember { mutableStateListOf(" zyp", " gary ") }
        val convertName by remember(list) { derivedStateOf { list.map { it.uppercase() } } }
        Column() {
            convertName.forEach {
                Text(text = it, Modifier.clickable {
                    list.add(" szx ")
                })
            }
        }
    }

}

@Composable
private fun remember4Parameter(name: String, onClick: () -> Unit) {
    /**
     * onClick点击事件可以触发页面内容的更新
     * 传入的name是一个String而不是mutableState对象，所以在remember4Parameter方法内部，无法订阅name的更新
     * 但是由于点击事件触发了name的改变，导致#1位置包括上下都会进行recompose，进行再次执行remember4Parameter()方法，并且传入了新的name值
     * 然后remember(name)判断前后两次name值不同，就会执行remember代码块内的代码
     */
    val convertName = remember(name) { name.uppercase() }
    Text(text = convertName, Modifier.clickable(onClick = onClick))
}

@Composable
private fun derived4Parameter(name: String, onClick: () -> Unit) {
    /**
     * onClick点击事件，不可以触发页面内容的更新，这是为什么呢
     * 传入的name是一个String而不是mutableState对象，所以在derived4Parameter()方法内部，无法订阅name的更新，即derivedStateOf也无法感知name的更新
     * 但是由于点击事件触发了name的改变，导致#1位置包括上下都会进行recompose，进行再次执行derived4Parameter()方法，并且传入了新的name值
     * 但是由于remember是无参的方法，即意味着参数永远不变，所以不会执行remember代码块内的代码
     */
    val convertName by remember { derivedStateOf { name.uppercase() } }
    Text(text = convertName, Modifier.clickable(onClick = onClick))
}

@Composable
private fun derived4StateOfParameter(name: MutableState<String>, onClick: () -> Unit) {
    /**
     * 如果传入的是一个MutableState对象，那么点击事件就可以触发数据更新到页面
     */
    val convertName by remember { derivedStateOf { name.value.uppercase() } }
    Text(text = convertName, Modifier.clickable(onClick = onClick))
}

@Composable
private fun rememberListParameter(data: List<String>, onClick: () -> Unit) {
    val convertName = remember(data) { data.map { it.uppercase() } }
    Column() {
        convertName.forEach {
            Text(text = it, Modifier.clickable(onClick = onClick))
        }
    }
}

@Composable
private fun derivedListParameter(data: List<String>, onClick: () -> Unit) {
    // 这样写，remember(data)来监听data对象被赋值了新对象，可以触发remember代码块内的代码执行
    // derivedStateOf来监听data内部的状态变化
    val convertName = remember(data) { derivedStateOf { data.map { it.uppercase() } } }
    Column() {
        convertName.value.forEach {
            Text(text = it, Modifier.clickable(onClick = onClick))
        }
    }
}
