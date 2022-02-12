package com.hahaha.composeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.hahaha.composeapplication.ui.theme.ComposeApplicationTheme

/**
 * compose独立于平台的概念：是指不依赖Android平台，可以独立于平台更新，
 * 类似Jetpack，不依赖Android的版本更新
 */


/**
 * compose 必须要在compose环境里面去执行，所以通过setContent去进入环境
 *
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            showText()
            showImage()
            showNetImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimages.yzz.cn%2F202105%2F202105291534381193.png&refer=http%3A%2F%2Fimages.yzz.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1647240521&t=b9e15e01fadd865b894569502687b5ff")
        }
    }
}

/**
 * 把UI代码抽出来，需要加@Composable注解，定义环境
 * 文本控件是Text(...)
 * 底层不是用的TextView控件，而是用的drawText() drawTextRun()等函数
 */
@Composable
private fun showText() {
    Text(text = "hahaha")
}

/**
 * 图片控件是Image(...)
 * Image支持两种格式
 * 1，ImageBitmap(和Bitmap一样)
 * 2，ImageVector SVG 矢量图
 *
 * painterResource = R.drawable.xxx (painter 和 Drawable类似)
 *
 * 访问网络图片，可以使用一个叫Coil的库
 * Coil地址：https://github.com/coil-kt/coil
 *
 * 底层用Canvas的drawBitmap
 */
@Composable
private fun showImage() {
    Image(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "")
}

/**
 * 访问网络图片，可以使用一个叫Coil的库
 * Coil地址：https://github.com/coil-kt/coil
 * 通过rememberImagePainter("http:xxx")来加载
 */
@Composable
private fun showNetImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Coil",
        modifier = Modifier.size(200.dp)
    )
}