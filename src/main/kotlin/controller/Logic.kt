package controller


import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Series
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.helper.HttpConnection
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.net.HttpURLConnection
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path

class Logic{

    val series: ArrayList<Series>

    init{
        initializeData()
        series = updateList()
    }


    private fun updateList():ArrayList<Series> {
        val retVal = ArrayList<Series>()
        val htmlDoc = Jsoup.connect("https://myanimelist.net/anime/season").get()
        val titles = htmlDoc.getElementsByClass("link-title")
        val descriptions = htmlDoc.getElementsByClass("preline")
        for (i in 0 until titles.size) {
            retVal.add(
                Series(
                    titles[i].text(),
                    descriptions[i].text()
                )
            )
        }
        return retVal
    }

    fun getDownloadLinks(title: String):HashMap<String,URI>{
        val retVal = HashMap<String,URI>()
        val queryTitle = title.replace(" ", "+") + "+sub"
        val doc: Document = Jsoup.connect("https://nyaa.si/?f=0&c=0_0&q=$queryTitle").get()
        val downloadElements = doc.getElementsByClass("success")

        downloadElements.forEach { x->
            val key = if(x.child(1).children().size == 2){
                x.child(1).child(1).text()
            } else {
                x.child(1).child(0).text()
            }

            val value = "https://nyaa.si/${x.child(2).child(0).attr("href")}"

            retVal[key] = URI(value)
        }
        return retVal
    }

    fun downloadShow(uri: URI, showName: String, fileName: String){
        val path = System.getProperty("user.home") + File.separator  + "Downloads" + File.separator + "ezweebk" + File.separator + showName + File.separator
        generateDownloadFolder(path)

        val targetFile = File(path+fileName)

        if(!targetFile.exists()) targetFile.createNewFile()

        val stream = uri.toURL().openStream()
        val fos = FileOutputStream(targetFile)

        fos.write(stream.readBytes())

        fos.flush()
        stream.close()
        fos.close()

        Window (
            size = IntSize(1200,100)
        ){
            Text(
                text = "File has been saved: ${targetFile.absolutePath}",
                modifier = Modifier.background(Color.LightGray).padding(4.dp).border(2.dp,Color.Cyan),
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp,
            )
        }
    }

    //TODO figure out images.
//    private fun getSeriesImageLinks(): ArrayList<String>{
//        val retVal = ArrayList<String>()
//        val doc: Document = Jsoup.connect("https://myanimelist.net/anime/season").get()
//        val elements = doc.getElementsByClass("image")
//        elements.forEach { e ->
//            retVal.add(e.children()[0].children()[0].attr("src"))
//        }
//        return retVal
//    }

    private fun initializeData(): String {
        val retData = getPath()
        generateDataFile(retData)
        return retData
    }

    private fun getPath(): String {
        var path = "${System.getProperty("user.home")}${File.separator}.local${File.separator}share${File.separator}ezweeb-k"

        val dir = File(path)
        if(!dir.exists()){
            dir.mkdirs()
        }

        path = "$path/ezweebk.json"

        return path
    }

    private fun generateDataFile(path: String) {
        val dataFile: File = File(path)

        if (!dataFile.exists()) {
            dataFile.createNewFile()
            writeDefault(dataFile)
        }
    }

    private fun generateDownloadFolder(path: String){
        val downloadDir = File(path)
        if(!downloadDir.exists()){
            downloadDir.mkdirs()
        }
    }


    private fun writeDefault(dataFile: File) {
        val fw = FileWriter(dataFile)
        fw.write(JSONObject().put("series_data", JSONArray()).toString())
        fw.flush()
        fw.close()
    }
}