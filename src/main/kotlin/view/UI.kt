package view

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import controller.Logic
import model.Series

fun root(logic: Logic) = Window(
    title = "EZWEEB-K",
    resizable = false,
    size = IntSize(800,600),
) {
    val stateVertical = rememberScrollState(0)
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(stateVertical)
        ) {
            Column {
                logic.series.forEach {
                    AnimeCard(it)
                }
            }
        }
    }
}

//@Composable
//fun AnimeRow(one: Series, two:Series?){
//    Row {
//        if(two!=null){
//            AnimeCard(one)
//            AnimeCard(two)
//        } else {
//            AnimeCard(one)
//        }
//    }
//}

@Composable
fun AnimeCard(series: Series){
    Card {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                Modifier.padding(4.dp).border(1.dp, Color.Cyan)
            ) {
                Image(
                    bitmap = imageFromResource("bae.jpg"),
                    "bae.jpg"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.border(1.dp, Color.Cyan)
            ) {
                Card{Text(series.title)}
                Card(Modifier.border(1.dp,Color.Cyan)){Text(series.description)}
                Card{Text("Anime Magnet Link")}
            }
        }
    }
}

//
@Composable
fun MagnetDropdown(mLinks: ArrayList<String>){
    var expanded by remember {mutableStateOf(false)}
    var selectedIndex by remember { mutableStateOf(0) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {expanded = false},
        modifier = Modifier.fillMaxWidth()
    ){
        mLinks.forEachIndexed { index, value ->
            DropdownMenuItem(
                onClick = {
                    selectedIndex = index
                    expanded = false
                },
                content = {Text(mLinks[index])}
            )
        }
    }
}