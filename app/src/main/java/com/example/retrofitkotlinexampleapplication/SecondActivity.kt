package com.example.retrofitkotlinexampleapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofitkotlinexampleapplication.databinding.ActivityMainBinding
import com.example.retrofitkotlinexampleapplication.databinding.ActivitySecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {
    private val binding: ActivitySecondBinding by lazy {
        ActivitySecondBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val retrofit =
            Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val jsonPlaceHolderApiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)

        binding.getDataBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                getData(jsonPlaceHolderApiInterface)
            }
        }
    }

    private suspend fun getData(api: ApiInterface) {
        try {
            val response = api.getPostUsingCoroutine()
            Log.i("Debugging!!", "Got Data  :$response")
            val listOfPosts = response
            if (listOfPosts != null) {
                for (post in listOfPosts) {
                    var Content = ""
                    Content += "ID:" + post?.id + "\n";
                    Content += "UserId: " + post?.userId + "\n";
                    Content += "Title: " + post?.title + "\n";
                    Content += "Text: " + post?.text + "\n\n";
                    binding.secondActivityTextView.setText(Content)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}