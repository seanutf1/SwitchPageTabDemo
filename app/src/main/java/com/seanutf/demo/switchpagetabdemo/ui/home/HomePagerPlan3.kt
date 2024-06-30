package com.seanutf.demo.switchpagetabdemo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomePagerPlan3(){
    val pagerState = rememberPagerState(pageCount = { 4 })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxHeight(),
    ) { _ ->
        val innerScrollableState = rememberPagerState(pageCount = { 6 })
        HorizontalPager(
            state = innerScrollableState,
            modifier = Modifier
                .background(color = Color.Gray)
                .fillMaxHeight()
        ) { pagePosition ->
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "页面：：= 外部${pagerState.currentPage}, 内部${innerScrollableState.currentPage}")
            }
        }
    }
}