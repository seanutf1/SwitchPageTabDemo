package com.seanutf.demo.switchpagetabdemo.ui.home

import androidx.lifecycle.ViewModel
import com.seanutf.demo.switchpagetabdemo.data.HomeDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {

    private val _tabList = MutableStateFlow(emptyList<UiTabList>())
    val tabList: StateFlow<List<UiTabList>> = _tabList

    private var currentPageIndex = 0
    private var currentTabIndex = 0

    fun setSelectPageAndTab() {
        if (currentPageIndex == 0) {
            val firstPageTabList =
                HomeDataUseCase.homeConfig.MainPage.TopNavs.mapIndexed{ index, nav ->
                    val select = index == currentTabIndex
                    UiTabList(nav.title, select)
                }
            _tabList.value = firstPageTabList
        } else {
            val otherPageTabList =
                HomeDataUseCase.homeConfig.MainPage.TopNavs[currentPageIndex].tags.mapIndexed{ index, tag ->
                    val select = index == currentTabIndex
                    UiTabList(tag.tag_name, select)
                }
            _tabList.value = otherPageTabList
        }
    }

    fun updateSelectIndex(index: Int) {
        if (currentPageIndex == 0) {
            currentPageIndex = index
            currentTabIndex = 0
        } else {
            currentTabIndex = index
        }
        setSelectPageAndTab()
    }
}

data class UiTabList(
    val title: String,
    val isSelect: Boolean = false,
)