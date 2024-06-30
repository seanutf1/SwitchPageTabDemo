package com.seanutf.demo.switchpagetabdemo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val topNavList by viewModel.topNavList.collectAsState()

        val topPagerState = rememberPagerState(pageCount = { topNavList.size })
        val childPagerState = rememberPagerState(
            pageCount = {
                viewModel.getChildPagerSizeOfTopTab(topPagerState.currentPage)
            }
        )
        val scope = rememberCoroutineScope()
        HomePagerPlan1(
            viewModel = viewModel,
            topPagerState = topPagerState,
            childPagerState = childPagerState,
            scope = scope,
        )
        HomeTabRow(
            modifier = Modifier.align(Alignment.TopCenter),
            viewModel = viewModel,
            onChildTabClick = { index ->
                viewModel.onChildTabClick(index)
                viewModel.notifyUiChangeSelectPage(topPagerState, childPagerState)
            }
        )

        LaunchedEffect(topPagerState, childPagerState) {

            snapshotFlow { topPagerState.currentPage to childPagerState.currentPage }.collect { page ->
                viewModel.onScrollToPage(page.first, page.second)
                viewModel.notifyUiChangeSelectPage(topPagerState, childPagerState)
            }
        }
    }
}

@Composable
fun HomeTabRow(
    modifier: Modifier,
    viewModel: HomeViewModel,
    onChildTabClick: (selectTabIndex: Int) -> Unit,
) {
    val tabList by viewModel.tabList.collectAsState()
    val selectedIndex  = tabList.indexOfFirst {
        it.isSelect
    }
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
            .background(
                color = Color.Cyan,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        tabList.forEachIndexed { index, topNav ->
            Tab(
                text = {
                    Text(
                        text = topNav.title,
                        color = if (topNav.isSelect) Color.Black else Color.Gray
                    )
                },
                selected = topNav.isSelect,
                onClick = {
                    onChildTabClick(index)
                }
            )
        }
    }
}