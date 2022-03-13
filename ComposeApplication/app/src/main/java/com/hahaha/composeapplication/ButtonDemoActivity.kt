package com.hahaha.composeapplication

import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.hahaha.composeapplication.ui.theme.ComposeApplicationTheme

/**
 * compose分包的group有七个 https://developer.android.google.cn/jetpack/androidx/releases/compose
 * 1,compose.animation 在 Jetpack Compose 应用中构建动画，丰富用户的体验。
 * 2,compose.compiler 借助 Kotlin 编译器插件，转换 @Composable functions（可组合函数）并启用优化功能。
 * 3,compose.foundation 使用现成可用的构建块编写 Jetpack Compose 应用，还可扩展 Foundation 以构建您自己的设计系统元素。
 * 4,compose.material 使用现成可用的 Material Design 组件构建 Jetpack Compose UI。这是更高层级的 Compose 入口点，旨在提供与 www.material.io 上描述的组件一致的组件。
 * 5,compose.material3 使用 Material Design 3（下一代 Material Design）组件构建 Jetpack Compose 界面。Material 3 包括更新后的主题和组件，以及动态配色等 Material You 个性化功能，旨在与新的 Android 12 视觉风格和系统界面相得益彰。
 * 6,compose.runtime Compose 的编程模型和状态管理的基本构建块，以及 Compose 编译器插件针对的核心运行时。
 * 7,compose.ui 与设备互动所需的 Compose UI 的基本组件，包括布局、绘图和输入。
 * Button
 */

class ButtonDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                showButton()
            }
        }
    }

    /**
     * Button自带的onClick在源码中，也是通过Modifier.clickable实现的
     */
    @Composable
    private fun showButton() {
        Button(
            onClick = {
                Toast.makeText(this, "button click", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.padding(30.dp)
        ) {
            Text(text = "Button")
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "button",
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
