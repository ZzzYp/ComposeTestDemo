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
 * Modifier
 * 1,设置边距：Compose通过Modifier.padding来设置边距
 *   原生使用margin设置外边距，padding设置内边距，这样做，主要是可以区分父子view的背景色，
 *   例如父view背景色是白色，子view背景色是黄色，使用padding设置间距，那么父view的背景色会被子view覆盖，这是就需要margin来设置边距了
 *   但是为什么Compose通过padding来设置边距就可以不用关注上述问题呢？，主要在于Modifier.padding和Modifier.background的调用顺序不同，
 *   那么实现的效果也不同，即Modifier对调用顺序是敏感的，具体可以看下面例子showPaddingBackground和showBackgroundPadding
 * 2，设置背景色
 * 3，设置宽高
 * 4，设置一些通用的属性
 *
 * 通用的设置用Modifier
 * 专项的设置用View本身的参数，比如文本大小，颜色等
 */

class ModifierDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                //showPaddingBackground()
                //showBackgroundPadding()
                //showPaddingBackgroundPadding()
                //showBackground()
                //showSize()
                //showText()
                showClick()
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
                .padding(top = 5.dp)
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
                .padding(top = 5.dp)
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


    /**
     * 第一个padding先执行，第二个padding后执行
     * 第一个padding不会覆盖第二个padding
     */
    @Composable
    private fun showPaddingBackgroundPadding() {
        Row(
            Modifier
                .padding(top = 5.dp)
                .background(Color.Blue)
                .size(200.dp)
        ) {
            Text(
                text = "zYp",
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(15.dp)
                    .background(Color.Red)
                    .padding(20.dp)
            )
        }
    }

    /**
     * background:
     * RoundedCornerShape 加圆角
     * clip 切割成某种形状，比如圆形CircleShape
     */
    @Composable
    private fun showBackground() {
        Row(
            Modifier
                .padding(top = 5.dp)
                .background(Color.Blue)
                .size(200.dp)
        ) {
            Text(
                text = "zYp",
                color = Color.DarkGray,
                modifier = Modifier
                    .background(Color.Red, RoundedCornerShape(5.dp))
                    .padding(15.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                "zyp",
                modifier = Modifier
                    .size(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                "zyp",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
        }
    }

    /**
     * Compose的宽高
     * 1，Modifier不设置size或者width，height。则和原生的warp_content一样
     * 2，Modifier设置fillMaxHeight,相当于原生的height = match_parent
     * 3，Modifier可以设置独立的宽高width和height，也可以通过size设置相同的宽高
     */
    @Composable
    private fun showSize() {
        Row(
            Modifier
                .size(200.dp)
                .background(Color.Blue)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "zYp",
                Modifier
                    .width(20.dp)
                    .height(40.dp)
                    .background(Color.Cyan)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "zYp",
                Modifier
                    .size(20.dp)
                    .background(Color.Red)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "zYp"
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "zYp",
                Modifier
                    .fillMaxHeight()
                    .background(Color.Black)
            )

        }
    }

    /**
     * fontSize 文字大小
     * color 文字颜色
     */
    @Composable
    private fun showText() {
        Text(
            text = "zYpo",
            fontSize = 20.sp,
            modifier = Modifier.background(Color.Red),
            color = Color.Blue
        )
    }

    /**
     * Modifier.clickable  设置点击事件
     */
    @Composable
    private fun showClick() {
        Text(
            text = "zYp",
            modifier = Modifier
                .padding(10.dp)
                .background(Color.Red)
                .clickable {
                    Toast.makeText(this,"click",Toast.LENGTH_LONG).show()
                }
                .padding(20.dp)
        )
    }
}
