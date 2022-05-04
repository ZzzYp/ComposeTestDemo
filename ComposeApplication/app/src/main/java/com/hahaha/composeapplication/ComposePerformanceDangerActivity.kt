package com.hahaha.composeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Compose 的编译器插件，会把可能需要重新调用的代码块包起来，
 * 在这个代码块执行结束以后，保存起来并标记当前执行的位置，
 * 当重新执行的条件达成以后（比如下面Text(text = name)中的name的值发生改变的时候），
 * 在标记的位置的代码块就会进行reCompose或者重组，
 * 具体来说，就是这个所在代码块会被重新拿出来执行一次并且它所依赖的变量值就是新的值了，就会组合出新的界面
 *
 *
 *
 */


class ComposePerformanceDangerActivity : ComponentActivity() {

    var name4 by mutableStateOf("zyp is niu")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                function1()
                function2()
                function3()
                function4(name4)
            }
        }
    }

    @Composable
    private fun function1() {
        /**
         * 在这段代码中，由于Text中的内容可能会发生改变，所以在编译过程中会被包起来(类似被注释的Function包起来)
         * 然后在name发生改变的时候，重新执行代码块，但是在代码块中，name的初始化也被包起来了，所以也会执行
         * 就会导致Text(text = name)中的name是一个新的初始化的值，所以才会发生协程中改变了name的值但是不生效的情况
         */
        // Function{
        var name by mutableStateOf("zyp is niu")
        Text(text = name)
        lifecycleScope.launch {
            delay(3000)
            name = "zyp is niu 123"
        }
        // }
    }

    @Composable
    private fun function2() {
        /**
         * 在这段代码中，包裹的作用域其实是在Button的位置，
         * 所以name的值发生变化以后，不会重新执行初始化的过程，相应的Text(text = name)中的name也会变成新值
         * 但是这样写也不好，因为改变了布局属性，从一个Text变成了一个Button
         */

        var name by mutableStateOf("zyp is niu")
        // Function{
        Button(onClick = { /*TODO*/ }) {
            Text(text = name)
        }
        //}
        lifecycleScope.launch {
            delay(3000)
            name = "zyp is niu 123"
        }
        // }
    }

    @Composable
    private fun function3() {
        /**
         * 在这段代码中，将mutableStateOf装在了一个remember包裹的代码块中
         * remember函数，它的作用就是第一次执行的时候，执行mutableStateOf并将结果返回，然后保存这个结果(即创建的MutableState对象)
         * 再次执行的时候，会直接返回保存的结果，起到了一个缓存的作用
         * 怎么判断一个变量是否处于会发生reCompose的范围内呢？ 无法判断-,-
         *
         */

        var name by remember { mutableStateOf("zyp is niu") }
        Text(text = name)
        lifecycleScope.launch {
            delay(3000)
            name = "zyp is niu 123"
        }
        // }
    }

    @Composable
    private fun function4(name: String) {
        /**
         * 在这段代码中，remember(name) { name.length },
         * 将name当作key，大括号内计算结果保存为value
         * 那么再次调用的时候，如果name不变就不会重新执行大括号内的代码，
         * 只有name发生了变化，才会再次执行大括号内的代码
         */
        val length = remember(name) { name.length }
        Text(text = name.plus(length))
        lifecycleScope.launch {
            delay(3000)
            name4 = "zyp is niu 123"
        }
    }
}


