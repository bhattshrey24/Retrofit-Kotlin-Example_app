package com.example.retrofitkotlinexampleapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitkotlinexampleapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.getDataFromApiBtn.setOnClickListener {
            binding.circularProgressBarMainActivity.visibility = View.VISIBLE
            getDataFromApi()
        }
        binding.gotToNextScreenBtn.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }

    private fun getDataFromApi() {
        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val jsonPlaceHolderApiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)

        val call = jsonPlaceHolderApiInterface.getPost()
        call.enqueue(
            object : Callback<List<Post?>?> {
                override fun onResponse(
                    call: Call<List<Post?>?>,
                    response: Response<List<Post?>?>
                ) {
                    binding.circularProgressBarMainActivity.visibility = View.GONE
                    if (!response.isSuccessful) {
                        binding.resultTextView.text = "Not successful, code:${response.code()}"
                        return
                    }
                    val listOfPosts = response.body()
                    if (listOfPosts != null) {
                        for (post in listOfPosts) {
                            var Content = ""
                            Content += "ID:" + post?.id + "\n";
                            Content += "UserId: " + post?.userId + "\n";
                            Content += "Title: " + post?.title + "\n";
                            Content += "Text: " + post?.text + "\n\n";
                            binding.resultTextView.append(Content);
                        }
                    }

                }

                override fun onFailure(call: Call<List<Post?>?>, t: Throwable) {
                    binding.circularProgressBarMainActivity.visibility = View.GONE
                    binding.resultTextView.text = t.message.toString()
                }
            })
    }
}