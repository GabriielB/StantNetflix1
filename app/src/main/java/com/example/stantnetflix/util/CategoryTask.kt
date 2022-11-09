package com.example.stantnetflix.util

import android.util.Log
import com.example.stantnetflix.modelo.Category
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.net.URLConnection
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask {
    fun execute(url: String){
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {


            val requestURL = URL(url)
            val urlConnection = requestURL.openConnection() as HttpsURLConnection
            urlConnection.readTimeout = 2000
            urlConnection.connectTimeout = 2000

            val statusCode = urlConnection.responseCode
            if(statusCode > 400){
                throw IOException("Erro na comunicação do servidor")
            }
               val stream =  urlConnection.inputStream
               val jsonAsString =  stream.bufferedReader().use { it.readText() }
                val categories = toCategories(jsonAsString)



            }catch (e: IOException){
                Log.e("Teste", e.message ?: "Erro Desconhecido", e)
            }
        }
    }
    private fun toCategories(jsonAsString: String) : List<Category>{
        val categories = mutableListOf<Category>()


        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("genres")

        for(i in 0 until jsonCategories.length()){
            val jsonCategory = jsonCategories.getJSONObject(i)
            val title = jsonCategory.getString("name")
        }
        return categories
    }
}