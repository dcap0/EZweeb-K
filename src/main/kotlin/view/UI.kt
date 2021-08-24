package view

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.Window
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import controller.Logic
import model.Series
import java.net.URI

fun root(logic: Logic) = Window(
    title = "EZWEEB-K",
    resizable = false,
    size = IntSize(800,600)
) {
    val stateVertical = rememberScrollState(0)
    Column (
        modifier = Modifier.background(Color.LightGray),
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
                    AnimeCard(it,logic)
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
fun AnimeCard(series: Series,logic: Logic){
    Card {
        var links by remember { mutableStateOf(HashMap<String, URI>())}

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(16.dp).background(Color.LightGray)
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
                modifier = Modifier.border(1.dp, Color.Cyan).background(Color.LightGray)
            ) {
                Card{Text(series.title)}
                Card(Modifier.border(1.dp,Color.Cyan)){Text(series.description)}
                if(links.isEmpty()) {
                    Button(
                        onClick = {
                            links = logic.getDownloadLinks(series.title)
                        }
                    ) {
                        Text("Get Download Links")
                    }
                } else {
                    Button(
                        onClick = {
                            links = logic.getDownloadLinks(series.title)
                        }
                    ) {
                        Text("Get Download Links")
                    }
                    Window(size = IntSize(650,400),) { LinkListColumn(links,logic,series.title) }
                }
            }
        }
    }
}

//ha
@Composable
fun LinkListColumn(links:HashMap<String,URI>, logic: Logic, showName: String){
    val labels = links.keys.toList()
    Card{
        LazyColumn() {
            itemsIndexed(items = labels){_, label ->
                DownloadCard(label,links[label]!!,logic,showName)
            }
        }
    }
}

@Composable
fun DownloadCard(label: String,uri: URI,logic: Logic,showName: String){
    Card(
        modifier = Modifier.clickable { logic.downloadShow(uri,showName,label) }
            .fillMaxWidth()
            .border(2.dp,Color.Cyan)
            .padding(bottom=4.dp)
    ){
        Text(
            text = label,
            modifier = Modifier.background(Color.LightGray),
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp
        )
    }
}
