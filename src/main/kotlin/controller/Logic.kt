package controller


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Series
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.logging.Logger

class Logic{
    init{
        initializeData()
    }

    fun updateList(){
        val retVal = ArrayList<Series>()
        val html = getHTML()
        CoroutineScope(Dispatchers.Default).launch { println(html) }
    }

    private fun getHTML(): String{
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder().apply {
            uri(URI.create("https://myanimelist.net/anime/season"))
            GET()
        }.build()

        val res: HttpResponse<String> = client.send(request, HttpResponse.BodyHandlers.ofString())

        return res.body()
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