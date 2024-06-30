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

    private lateinit var uiHomePageTabList: List<UiTopNav>

    init {
        transform()
        notifyUiTabList()
    }

    /**
     * 将原始api数据转换成UI展示数据
     * */
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

    /**
     * 更新Ui展示的Tab 列表
     * */
    private fun notifyUiTabList() {
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
                    val select = index == getPageSelectTabIndex(currentPageIndex)
                    UiTabList(tag.tag_name, select)
                }
            _tabList.value = otherPageTabList
        }
    }

    /**
     * 点击Tab
     * */
    fun onChildTabClick(tabIndex: Int) {
        updateSelectTab(tabIndex)
        notifyUiTabList()
    }

    /**
     * 更新选中的Tab
     * */
    private fun updateSelectTab(tabIndex: Int) {
        if (currentPageIndex == 0) {
            //recommend page
            if (getPageSelectTabIndex(0) != tabIndex) {
                //update page index
                currentPageIndex = tabIndex
                //reset tab index to zero
                updateSpecificPageSelectTabIndex(tabIndex, 0)
            }
        } else {
            //other page
            updateCurrPageSelectTabIndex(tabIndex)
        }
    }

    /**
     * 获取每个子页面的Tab数量
     * */
    fun getChildPagerSizeOfTopTab(currTopTabIndex: Int): Int {
        return uiHomePageTabList[currTopTabIndex].tags.size
    }

    /**
     * 获取每个子页面中当前选中Tab的Index
     * */
    private fun getPageSelectTabIndex(pageIndex: Int): Int {
        return uiHomePageTabList[pageIndex].selectIndex
    }

    /**
     * 更新当前页面中选中Tab的Index
     * */
    private fun updateCurrPageSelectTabIndex(tabIndex: Int) {
        updateSpecificPageSelectTabIndex(currentPageIndex, tabIndex)
    }

    /**
     * 更新指定页面中选中Tab的Index
     * */
    private fun updateSpecificPageSelectTabIndex(pageIndex: Int, tabIndex: Int) {
        uiHomePageTabList[pageIndex].selectIndex = tabIndex
    }

    /**
     * 通知Ui中的Page和child Page页面更新
     * */
    fun notifyUiChangeSelectPage(topPagerState: PagerState, childPagerState: PagerState) {
        viewModelScope.launch {
            topPagerState.scrollToPage(currentPageIndex)
            childPagerState.scrollToPage(getPageSelectTabIndex(currentPageIndex))
        }
    }

    /**
     * 获取当前页面的页面数据
     * */
    fun getCurrentPageData(currentPage: Int): UiTopNav {
        return uiHomePageTabList[currentPage]
    }

    /**
     * 滑动页面处理
     * */
    fun onScrollToPage(pageIndex: Int, tabIndex: Int) {
        val lastTimePageIndex = currentPageIndex
        if (pageIndex < lastTimePageIndex) {
            val lastIndex = uiHomePageTabList[pageIndex].tags.lastIndex
            updateSpecificPageSelectTabIndex(pageIndex, lastIndex)
        } else if (pageIndex > lastTimePageIndex) {
            updateSpecificPageSelectTabIndex(pageIndex, 0)
        } else {
            updateSpecificPageSelectTabIndex(pageIndex, tabIndex)
        }
        currentPageIndex = pageIndex
        notifyUiTabList()
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