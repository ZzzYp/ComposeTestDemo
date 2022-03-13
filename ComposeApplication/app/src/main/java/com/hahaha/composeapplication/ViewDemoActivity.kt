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
 * FrameLayout   ->  Box()
 * LinearLayout  ->  Column() and Row()
 * RelativeLayout  ->  Box() + Modifier
 * ConstraintLayout
 * ScrollView  -> Modifier.horizontalScroll() or Modifier.verticalScroll
 * RecyclerView  ->  LazyColumn() or LazyRow()
 * ViewPager -> Pager()
 */

class ViewDemoActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //showFrameLayout()
            //showLinearLayout()
            //showRelativeLayout()
            //showRecyclerView()
            //showHorizontalRecyclerView()
            //showScrollView()
            showViewPager()
        }
    }
}

/**
 * showFrameLayout
 * Box == Android`s FrameLayout
 */
@Preview()
@Composable
private fun showFrameLayout() {
    Box(Modifier.background(Color.Red)) {
        netImage()
        localImage()
    }
}

/**
 * showLinearLayout
 * Column and Row == Android`s LinearLayout
 */
@Composable
private fun showLinearLayout() {
    Column() {
        netImage()
        Row {
            localImage()
            stringText()
        }
    }
}

/**
 * showRelativeLayout
 * Box + Modifier == Android`s RelativeLayout
 */
@Composable
private fun showRelativeLayout() {
    // todo
    Box() {
        netImage()
        localImage()
        stringText()
    }
}

/**
 * showRecyclerView
 * LazyColumn == Android`s RecyclerView
 */
@Composable
private fun showRecyclerView() {
    val values = arrayListOf<String>()
    for (index in 0..10) {
        values.add("index $index")
    }
    LazyColumn {
        // 设置单行Item
        item {
            netImage()
        }
        // 设置多行Item，参数为list集合
        items(values) { valueStr ->
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp)
                )
                Text(
                    text = valueStr,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 10.dp),
                    fontSize = 16.sp,
                )
            }
        }
        item {
            netImage()
        }
        item {
            netImage()
        }
        items(values) { valueStr ->
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp)
                )
                Text(
                    text = valueStr,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 10.dp),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

/**
 * showHorizontalRecyclerView
 * LazyRow == Android`s Horizontal RecyclerView
 */
@Composable
private fun showHorizontalRecyclerView() {
    val values = arrayListOf<String>()
    for (index in 0..10) {
        values.add("index $index")
    }
    LazyRow {
        // 设置单行Item
        item {
            netImage()
        }
        // 设置多行Item，参数为list集合
        items(values) { valueStr ->
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp)
                )
                Text(
                    text = valueStr,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 10.dp),
                    fontSize = 16.sp,
                )
            }
        }
        item {
            netImage()
        }
        item {
            netImage()
        }
        items(values) { valueStr ->
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp)
                )
                Text(
                    text = valueStr,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 10.dp),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

/**
 * showScrollView
 * Modifier.horizontalScroll() or Modifier.verticalScroll() == Android`s ScrollView
 */
@Composable
private fun showScrollView() {
    Column() {
        // 横向ScrollView
        Row(Modifier.horizontalScroll(state = rememberScrollState(), enabled = true)) {
            netImage()
            stringText()
            localImage()
            stringText()
            localImage()
            netImage()
            netImage()
            localImage()
            localImage()
            localImage()
            netImage()
            localImage()
        }
        // 纵向ScrollView
        Column(Modifier.verticalScroll(state = rememberScrollState())) {
            netImage()
            stringText()
            localImage()
            stringText()
            localImage()
            netImage()
            netImage()
            localImage()
            localImage()
            localImage()
            netImage()
            localImage()
        }
    }
}

/**
 * showViewPager
 * HorizontalPager() or VerticalPager() == Android`s ViewPager
 * https://google.github.io/accompanist/pager/
 * https://github.com/google/accompanist
 */
@ExperimentalPagerApi
@Composable
private fun showViewPager() {
    // todo 目前pager还在Accompanist库中，意味着api还不稳定
    HorizontalPager(count = 5, state = rememberPagerState()) { page ->
        Text(text = "page $page", modifier = Modifier.fillMaxWidth())
    }
}


@Composable
private fun stringText() {
    Text(text = "hahaha")
}


@Composable
private fun localImage() {
    Image(
        painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "",
        modifier = Modifier.background(Color.LightGray)
    )
}

@Composable
private fun netImage() {
    val imageUrl =
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimages.yzz.cn%2F202105%2F202105291534381193.png&refer=http%3A%2F%2Fimages.yzz.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1647240521&t=b9e15e01fadd865b894569502687b5ff"
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Coil",
        modifier = Modifier.size(200.dp)
    )
}