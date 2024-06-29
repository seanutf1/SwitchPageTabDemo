package com.seanutf.demo.switchpagetabdemo.data

data class HomeConfig(
    val MainPage: MainPage
)

data class MainPage(
    val TopNavs: List<TopNav>,
    val ShakingFloatingButton: List<Button>,
    val tabbar: Tabbar,
)

data class TopNav(
    val title: String,
    val nav: String,
    val tags: List<Tag>,
)

data class Tag(
    val tag_name: String,
    val tag_type: String,
    val tag_type_id: Int,
)

data class Button(
    val title: String,
    val icon_selected: String,
    val icon_normal: String,
    val text_color_selected: String,
    val text_color_normal: String,
    val nav: String,
)

data class Tabbar(
    val bg_normal: String,
    val middle: Middle,
)

data class Middle(
    val icon_bg: String,
    val icon_normal: String,
    val title: String,
    val icon_show: String,
    val buttons: List<Button>,
    val normal: List<Button>,
)
