package com.seanutf.demo.switchpagetabdemo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll

/**
 * https://github.com/Ovaltinezz/nested-horizontal-pager
 * */
@Composable
fun HomePagerPlan2() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxHeight(),
        pageNestedScrollConnection = NoOpNestedScrollConnection
    ) { _ ->
        val innerScrollableState = rememberPagerState(pageCount = { 6 })
        val coordinatingNestedScroll = remember(innerScrollableState, innerScrollableState) {
            coordinatingPagerNestedScroll(innerScrollableState, innerScrollableState)
        }
        SecondPager(Modifier.nestedScroll(coordinatingNestedScroll), innerScrollableState)
    }
}

@Composable
private fun SecondPager(modifier: Modifier, pagerState: PagerState) {
    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .background(color = Color.Gray)
                .fillMaxHeight()
        ) { pagePosition ->
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "页面：：= 外部${pagePosition}, 内部${pagePosition}")
            }
        }
    }
}

private val NoOpNestedScrollConnection = object : NestedScrollConnection {}

private fun coordinatingPagerNestedScroll(
    outerPagerState: PagerState,
    innerScrollableState: ScrollableState
) = object : NestedScrollConnection {
    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        return if ((available.x > 0 && !innerScrollableState.canScrollForward && outerPagerState.currentPageOffsetFraction != 0f) ||
            (available.x < 0 && !innerScrollableState.canScrollBackward && outerPagerState.currentPageOffsetFraction != 0f)
        ) {
            Offset.Zero.copy(x = -outerPagerState.dispatchRawDelta(-available.x))
        } else {
            super.onPreScroll(available, source)
        }
    }
}