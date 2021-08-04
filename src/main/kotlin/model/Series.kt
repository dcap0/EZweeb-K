package model

data class Series(
    val title: String,
    val description: String,
    val magnetLinks: ArrayList<String>
)

//fun Series.toJSONObject(){
//    return JSONObject()
//}