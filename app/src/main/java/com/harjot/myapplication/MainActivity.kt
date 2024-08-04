package com.harjot.myapplication

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.harjot.myapplication.databinding.ActivityDetailsDialogBinding
import com.harjot.myapplication.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(),JsonInterface {
    lateinit var binding: ActivityMainBinding
    var array = ArrayList<JsonModel>()
    lateinit var mediaPlayer: MediaPlayer
    var jsonAdapter = JsonAdapter(array,this)
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

    override fun onListClick(position: Int) {
        Dialog(this).apply {
            var dialogBinding = ActivityDetailsDialogBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            dialogBinding.btnPlay.setOnClickListener {
                Toast.makeText(this@MainActivity, "Play", Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer()
                try {
                    val assetFileDescriptor = assets.openFd("audios/soch_v_ni_skda.mp3")
                    mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                    )
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            dialogBinding.btnPause.setOnClickListener {
                Toast.makeText(this@MainActivity, "Pause", Toast.LENGTH_SHORT).show()
                if (::mediaPlayer.isInitialized) {
                    mediaPlayer.release()
                }
            }
            show()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
