package controller


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileWriter
import java.net.URI

class Logic{
    init{
        initializeData()
        updateList()
    }

    fun updateList(){
        val title = getSeriesData("link-title")
        val preline = getSeriesData("preline")

//        CoroutineScope(Dispatchers.Default).launch {
//
//            for(i in 0 until title.size){
//                println("${title[i]}: ${preline[i]}\n")
//            }
//
//        }
    }

    private fun getMagnetLinks(title: String){
        val retVal = ArrayList<URI>()
        val queryTitle = title.replace(" ", "+") + "+horriblesubs"
        val doc: Document = Jsoup.connect("https://nyaa.si/?f=0&c=0_0&q=$queryTitle").get()
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

    private fun getSeriesData(htmlClass: String): ArrayList<String>{
        val retVal = ArrayList<String>()
        val doc: Document = Jsoup.connect("https://myanimelist.net/anime/season").get()
        val titles = doc.getElementsByClass(htmlClass)
        titles.forEach { retVal.add(it.text()) }
        return retVal
    }


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

    private fun writeDefault(dataFile: File) {
        val fw = FileWriter(dataFile)
        fw.write(JSONObject().put("series_data", JSONArray()).toString())
        fw.flush()
        fw.close()
    }
}