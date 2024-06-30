package com.seanutf.demo.switchpagetabdemo.ui.home

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seanutf.demo.switchpagetabdemo.data.HomeDataUseCase.originalHomeConfig
import com.seanutf.demo.switchpagetabdemo.data.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    //current shou page: default: recommend
    private var currentPageIndex = 0

    //for recommend page's and other page's tabs size
    private val _tabList = MutableStateFlow(emptyList<UiTabList>())
    val tabList: StateFlow<List<UiTabList>> = _tabList

    //for recommend page's and other page's pages size
    private val _topNavList = MutableStateFlow(emptyList<UiTopNav>())
    val topNavList: StateFlow<List<UiTopNav>> = _topNavList

    //for home page's select child page
    private val _topPageSelectIndex = MutableStateFlow(currentPageIndex)
    val topPageSelectIndex: StateFlow<Int> = _topPageSelectIndex

    //for recommend page's and other page's select child page
    private val _childPageSelectIndex = MutableStateFlow(0)
    val childPageSelectIndex: StateFlow<Int> = _childPageSelectIndex

    private lateinit var uiHomePageTabList: List<UiTopNav>

    init {
        transform()
        setSelectPageAndTab()
    }

    private fun transform() {
        uiHomePageTabList = originalHomeConfig.MainPage.TopNavs.map {
            UiTopNav(
                title = it.title,
                nav = it.nav,
                tags = it.tags
            )
        }
        _topNavList.value = uiHomePageTabList
    }

    private fun setSelectPageAndTab() {
        if (currentPageIndex == 0) {
            val firstPageTabList =
                uiHomePageTabList.mapIndexed { index, nav ->
                    val select = index == 0
                    UiTabList(nav.title, select)
                }
            _tabList.value = firstPageTabList
        } else {
            val otherPageTabList =
                uiHomePageTabList[currentPageIndex].tags.mapIndexed { index, tag ->
                    val select = index == getPageCurrSelectTabIndex(currentPageIndex)
                    UiTabList(tag.tag_name, select)
                }
            _tabList.value = otherPageTabList
        }
    }

    fun updateSelectIndex(tabIndex: Int) {
        updateSelectTab(tabIndex)
        setSelectPageAndTab()
        _topPageSelectIndex.value = currentPageIndex
        _childPageSelectIndex.value = getPageCurrSelectTabIndex(currentPageIndex)
    }

    private fun updateSelectTab(tabIndex: Int): Boolean {
        return if (currentPageIndex == 0) {
            //recommend page
            if (getPageCurrSelectTabIndex(0) != tabIndex) {
                //update page index
                currentPageIndex = tabIndex
                //reset tab index to zero
                updateCurrPageSelectTabIndex(0)
                true
            } else {
                false
            }
        } else {
            //other page
            updateCurrPageSelectTabIndex(tabIndex)
            false
        }
    }

    fun getChildPagerSizeOfTopTab(currTopTabIndex: Int): Int {
        return uiHomePageTabList[currTopTabIndex].tags.size
    }

    private fun getPageCurrSelectTabIndex(pageIndex: Int): Int {
        return uiHomePageTabList[pageIndex].selectIndex
    }

    private fun updateCurrPageSelectTabIndex(tabIndex: Int) {
        updateSpecificPageSelectTabIndex(currentPageIndex, tabIndex)
    }

    private fun updateSpecificPageSelectTabIndex(pageIndex: Int, tabIndex: Int) {
        uiHomePageTabList[pageIndex].selectIndex = tabIndex
    }

    fun updateSelectTabUi(topPagerState: PagerState, childPagerState: PagerState) {
        viewModelScope.launch {
            topPagerState.scrollToPage(currentPageIndex)
            childPagerState.scrollToPage(getPageCurrSelectTabIndex(currentPageIndex))
        }
    }

    fun getCurrentPageData(currentPage: Int): UiTopNav {
        return uiHomePageTabList[currentPage]
    }

    fun onScrollToPage(pageIndex: Int) {
        //val thisTimeSelectTabIndex = getPageCurrSelectTabIndex(pageIndex)
        val lastTimePageIndex = currentPageIndex
        //val lastTimeSelectTabIndex = getPageCurrSelectTabIndex(currentPageIndex)

        if (pageIndex < lastTimePageIndex) {
            val lastIndex = uiHomePageTabList[pageIndex].tags.lastIndex
            updateSpecificPageSelectTabIndex(pageIndex, lastIndex)
        } else {
            updateSpecificPageSelectTabIndex(pageIndex, 0)
        }
        currentPageIndex = pageIndex
        setSelectPageAndTab()
        _topPageSelectIndex.value = currentPageIndex
        _childPageSelectIndex.value = getPageCurrSelectTabIndex(currentPageIndex)
    }
}

data class UiTabList(
    val title: String,
    val isSelect: Boolean = false,
)

data class UiTopNav(
    val title: String,
    val nav: String,
    val tags: List<Tag>,
    var selectIndex: Int = 0
)