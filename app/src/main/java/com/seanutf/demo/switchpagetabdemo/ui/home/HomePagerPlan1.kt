package com.seanutf.demo.switchpagetabdemo.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.seanutf.demo.switchpagetabdemo.ui.utils.rememberDraggablePagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePagerPlan1(
    viewModel: HomeViewModel,
    topPagerState: PagerState,
    childPagerState: PagerState,
    scope: CoroutineScope,
) {
    HorizontalPager(
        state = topPagerState,
        modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxHeight(),
    ) { _ ->
        ChildPager(topPagerState, childPagerState, scope, viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChildPager(
    topPagerState: PagerState,
    childPagerState: PagerState,
    scope: CoroutineScope,
    viewModel: HomeViewModel,
){
    val draggableState = rememberDraggablePagerState(topPagerState, childPagerState)
    draggableState.initUserScrollEnableType()
    HorizontalPager(
        userScrollEnabled = draggableState.userScrollEnabled(),
        state = childPagerState,
        modifier = Modifier
            .background(color = Color.Gray)
            .fillMaxHeight()
            .draggable(
                state = rememberDraggableState { onDetail ->
                    scope.launch {
                        draggableState.setDraggableOnDetailToScrollToPage(onDetail)
                    }
                },
                orientation = Orientation.Horizontal,
                enabled = (draggableState.draggableEnabled())
            ),
    ) { _ ->
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val currNavData = viewModel.getCurrentPageData(topPagerState.currentPage)
            Text(text = "当前页面：外部Tab:位置${topPagerState.currentPage},名称${currNavData.title}, 内部:位置${childPagerState.currentPage}, 名称${currNavData.tags[childPagerState.currentPage].tag_name}")
        }
    }
}