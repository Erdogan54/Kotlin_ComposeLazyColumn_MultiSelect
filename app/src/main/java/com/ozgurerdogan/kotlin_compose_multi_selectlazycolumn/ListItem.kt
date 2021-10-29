package com.ozgurerdogan.kotlin_compose_multi_selectlazycolumn

import androidx.compose.ui.graphics.Color


data class ListItem(
    val title:String,
    var isSelected:Boolean,

)

data class List2Model(
    val title:String,
    var color: Color
)