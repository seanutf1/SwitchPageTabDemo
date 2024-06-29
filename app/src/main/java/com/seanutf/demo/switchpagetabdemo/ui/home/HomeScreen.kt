package com.seanutf.demo.switchpagetabdemo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
){
    viewModel.setSelectPageAndTab()
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomePagerPlan1()
        HomeTabRow(
            modifier = Modifier.align(Alignment.TopCenter),
            viewModel = viewModel,
        )

    }

}

@Composable
fun HomeTabRow(
    modifier: Modifier,
    viewModel: HomeViewModel,
) {
    val tabList by viewModel.tabList.collectAsState()
    var selectedIndex  = tabList.indexOfFirst {
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
                        color = if (topNav.isSelect) Color.White else Color.LightGray
                    )
                },
                selected = topNav.isSelect,
                onClick = {
                    viewModel.updateSelectIndex(index)
                }
            )
        }
    }
}
