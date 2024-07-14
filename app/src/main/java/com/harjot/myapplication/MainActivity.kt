package com.harjot.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.harjot.myapplication.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var array = ArrayList<JsonModel>()
    var jsonAdapter = JsonAdapter(array)
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = jsonAdapter

        readJson()
    }

    fun readJson() {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open("first.json")
            json = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                array.add(JsonModel(
                    name = jsonObj.getString("name"),
                    image = jsonObj.getString("image")
                ))
            }
            jsonAdapter.notifyDataSetChanged()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}
