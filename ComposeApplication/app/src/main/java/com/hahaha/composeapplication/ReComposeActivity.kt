package com.hahaha.composeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Recompose 重组的性能风险和智能优化
 *
 *
 */


class ReComposeActivity : ComponentActivity() {

    var name by mutableStateOf("zyp is niu name")
    var name1 by mutableStateOf("zyp is niu name1")
    var dataUserInfo = DataUserInfo("zyp -> Data")
    var userInfo = UserInfo("zyp -> Unit")
    var userInfoVal1 = UserInfoVal("zyp -> Unit")
    var userInfoVal2 = UserInfoVal("zyp -> Unit")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /**
             * 当我们触发Text(text = name, Modifier.clickable { name = "zyp is big niu " }) 中Text的click事件，会发现println("Recompose 1")又执行了一遍
             * 这是因为Column是一个inline函数，会把内部代码，包括大括号内的代码拷贝到调用的地方
             * 而Text中的text参数发生改变的时候，会触发Compose的ReCompose即重组(代码重新执行)，
             * 而重组的范围不只是在Text内部，而是和Text同一层的代码都会被重新执行，所以println("Recompose 1")会被重新执行一遍
             *
             * 这样会引起性能风险
             *
             * 但是，我们会发现function1()中的println("Recompose 3")并没有打印，因为编译器在编译代码的时候，会加判断，
             * 如果function1()内部的Text的text参数没有发生变化，那么就不会执行function1()内部的代码，这样就不会引起性能问题
             *
             * 相反，当点击事件改变了name的value，function2(name)传入的是name参数，这个时候上述的判断会发现name的值发生了变化
             * 所以function2(name)会重新执行
             */
            println("Recompose 1")
            Column {
                println("Recompose 2")
                Text(text = name, Modifier.clickable {
                    name = "click "
                    // 测试不同对象对于自动更新的过滤
                    //dataUserInfo = DataUserInfo("zyp -> Data")
                    //userInfo = UserInfo("zyp -> Unit")
                    // 测试相同对象不同内容
                    //dataUserInfo.name = "zyp -> Data"
                    //userInfo.name = "zyp -> Unit"
                    userInfoVal1 = userInfoVal2
                })
                function1()
                function2(name)
                function3(dataUserInfo)
                function4(userInfo)
                function5(userInfoVal1)
            }
        }
    }

    @Composable
    private fun function1() {
        println("Recompose 3")
        Text(text = name1,
            modifier = Modifier.apply {
                clickable { name1 = "zyp is big niu name1 哇 " }
                padding(20.dp)
            }
        )
    }

    @Composable
    private fun function2(name: String) {
        println("Recompose 4")
        Text(text = name,
            modifier = Modifier
                .apply {
                    padding(20.dp)
                }
                .clickable { this.name = "zyp is big niu 哇 name 4 " })
    }

    @Composable
    private fun function3(dataUserInfo: DataUserInfo) {
        println("Recompose 5")
        Text(text = "function3 ${dataUserInfo.name}",
            modifier = Modifier.apply {
                padding(20.dp)
            })
    }

    @Composable
    private fun function4(userInfo: UserInfo) {
        println("Recompose 6")
        Text(text = "function4 ${userInfo.name}",
            modifier = Modifier.apply {
                padding(20.dp)
            })
    }

    @Composable
    private fun function5(userInfoVal: UserInfoVal) {
        println("Recompose 7")
        Text(text = "function5 ${userInfoVal.name}",
            modifier = Modifier.apply {
                padding(50.dp)
            })
    }
}

// 当name是val修饰参数的时候，Compose会认为这是一个可靠的类，会在reCompose过程中，用==判断
// 当name是var修饰参数的时候，Compose会认为这是一个不可靠的类，在reCompose过程中，不会进行判断，而是直接进行reCompose即重新执行一遍代码
// 因为是var的时候，compose不知道什么时候会被改变，所以每次reCompose都默认var修饰的参数发生了改变，重新执行reCompose代码块
// 那么是否可以自己设置在reCompose的时候，不执行某个方法内部的reCompose吗，可以用@Stable注解
// 但是当DataUserInfo里面的name发生变更的时候，要通知到Compose进行Composition，就要是用mutableStateOf了，写法如下#1
@Stable
data class DataUserInfo(var name: String)

class UserInfo(var name: String) {

}

// #1
class UserInfoByMutableStateOF(name: String) {
    var name by mutableStateOf(name)
}

//@Stable
data class UserInfoVal(val name: String) {

}


