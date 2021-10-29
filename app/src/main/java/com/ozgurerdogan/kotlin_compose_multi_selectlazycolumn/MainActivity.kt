package com.ozgurerdogan.kotlin_compose_multi_selectlazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.VectorConverter
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Share

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

import com.ozgurerdogan.kotlin_compose_multi_selectlazycolumn.ui.theme.Kotlin_Compose_MultiSelectLazyColumnTheme


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kotlin_Compose_MultiSelectLazyColumnTheme {
                window.statusBarColor = MaterialTheme.colors.primaryVariant.toArgb()

            MultiSelectLazy()

            }

        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeList() {

    Scaffold(topBar = { MainTopBar() }) {
        val ss = remember {
            mutableStateListOf(
                "Item Number 1",
                "Item Number 2",
                "Item Number 3",
                "Item Number 4",
                "Item Number 1",
                "Item Number 2",
                "Item Number 3",
                "Item Number 4"
            )
        }

        LazyColumn {
            itemsIndexed(
                items = ss,
                key = { index, item ->
                    item.hashCode()
                }
            ) { index, item ->

                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            ss.remove(item)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = state,
                    background = {
                        val color = when (state.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color.Red
                            null -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete", tint = Color.White,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    },
                    dismissContent = {
                        MyCustomItem(text = item)
                    },
                    directions = setOf(DismissDirection.EndToStart)

                )
                Divider()

            }
        }
    }
}

@Composable
fun MainTopBar() {
    TopAppBar(
        title = { Text(text = "Swipe To Delete")}
    )
}

@Composable
fun MultiSelectLazy() {
    var rows by remember{
        mutableStateOf(
            (1..20).map {
                ListItem(
                    title="Item $it",
                    isSelected = false
                )
            }
        )
    }

    //rows.filter { it.isSelected }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(rows.size){i->
            Row(
                modifier = Modifier
                    .background(color = Color.Yellow, RectangleShape)
                    .fillMaxWidth()
                    .clickable {

                        println("item index= $i " + rows[i].title + " before: ${rows[i].isSelected}")

                        rows = rows.mapIndexed { index, item ->
                            if (i == index) {
                                item.copy(isSelected = !item.isSelected)
                            } else item
                        }

                        println("item index= $i " + rows[i].title + " click")
                        println("item index= $i " + rows[i].title + " after: ${rows[i].isSelected}")

                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = rows[i].title,
                    fontSize = 22.sp,
                    color= Color.Blue,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Default,
                    modifier = Modifier
                        .padding(10.dp),
                    style = MaterialTheme.typography.button


                )
                if (rows[i].isSelected){
                    Icon(
                        imageVector =Icons.Default.CheckCircle ,
                        contentDescription ="selected",
                        tint = Color.Green,
                        modifier= Modifier
                            .size(30.dp)
                            .offset(x = (-20).dp)
                    )

                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black, Color.Red
                        )
                    )
                )
                .height(7.dp)

            )
        }
    }
}

@Composable
fun DenemeList() {
    var list1 by remember {
        mutableStateOf(
            (1..20).map {
                List2Model(title = "Deneme $it", color = Color.LightGray)

            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        items(list1.size) { index ->
            Row(
                modifier = Modifier
                    .clickable {
                        list1 = list1.mapIndexed { i, item ->
                            if (i == index) {
                                if (list1[i].color == Color.LightGray) {
                                    item.copy(color = Color.Blue)
                                } else {
                                    item.copy(color = Color.LightGray)
                                }

                            } else item
                        }

                    }
                    .height(45.dp)
                    .fillMaxWidth()
                    .background(list1[index].color),

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = list1[index].title)

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    modifier = Modifier
                        .clickable {

                        }
                )

            }

        }

    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyCustomItem(text:String) {
    ListItem (
        text = { Text(text = text)},
        overlineText = { Text(text = "overline")},
        icon = { Icon(imageVector = Icons.Outlined.Share, contentDescription =null )},
        trailing = { Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)},
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    )

}





