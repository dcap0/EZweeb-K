package view

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.dp

fun root() = Window(
    title = "EZ-WEEB",
    resizable = false
) {
    val stateVertical = rememberScrollState(0)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(stateVertical)
            .padding(8.dp)
    ) {
        Column {
            AnimeRow()
            AnimeRow()
            AnimeRow()
            AnimeRow()
            AnimeRow()
        }
    }
}

@Composable
fun AnimeRow(){
    Row {
        AnimeCard()
        AnimeCard()
    }
}

@Composable
fun AnimeCard(){
    Card {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Image(
                    bitmap = imageFromResource("bae.jpg"),
                    "bae.jpg"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Card{Text("Anime Name")}
                Card{Text("Anime Description")}
                Card{Text("Anime Magnet Link")}
            }
        }
    }
}