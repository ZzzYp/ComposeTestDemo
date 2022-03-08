package com.hahaha.composeapplication

import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * Modifier
 * 1,设置边距：Compose通过Modifier.padding来设置边距
 *   原生使用margin设置外边距，padding设置内边距，这样做，主要是可以区分父子view的背景色，
 *   例如父view背景色是白色，子view背景色是黄色，使用padding设置间距，那么父view的背景色会被子view覆盖，这是就需要margin来设置边距了
 *   但是为什么Compose通过padding来设置边距就可以不用关注上述问题呢？，主要在于Modifier.padding和Modifier.background的调用顺序不同，
 *   那么实现的效果也不同，即Modifier对调用顺序是敏感的，具体可以看下面例子showPaddingBackground和showBackgroundPadding
 *2，设置背景色
 *
 */

class ModifierDemoActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                showPaddingBackground()
                showBackgroundPadding()
            }
        }
    }


    /**
     * 先执行padding后执行background，和原生margin效果相同
     */
    @Composable
    private fun showPaddingBackground() {
        Row(
            Modifier
                .background(Color.Blue)
                .size(200.dp)
        ) {
            Text(
                text = "zYp",
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(20.dp)
                    .background(Color.Red)
            )
        }
    }

    /**
     * 先执行background后执行padding，和原生padding效果相同
     */
    @Composable
    private fun showBackgroundPadding() {
        Row(
            Modifier
                .background(Color.Blue)
                .size(200.dp)
        ) {
            Text(
                text = "zYp",
                color = Color.DarkGray,
                modifier = Modifier
                    .background(Color.Red)
                    .padding(20.dp)
            )
        }
    }
}
