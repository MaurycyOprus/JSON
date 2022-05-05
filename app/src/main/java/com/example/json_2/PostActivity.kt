package com.example.json_2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.ArrayList

class PostActivity : AppCompatActivity() {

    private fun getPosts(id: Int): ArrayList<Post>{
        val url = "https://jsonplaceholder.typicode.com/posts"
        val postsAll: ArrayList<Post> = arrayListOf()
        val posts = URL(url).readText()

        try{
            val data = JSONArray(posts)
            for(i in 0 until data.length()){
                val comment: JSONObject = data.getJSONObject(i)
                if(comment.getInt("userId") == id) {
                    postsAll.add(Post(
                        comment.getInt("id"),
                        comment.getString("title"),
                        comment.getString("body")
                    ))
                }
            }
        } catch(e: JSONException){ }
        return postsAll
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val actionBar = supportActionBar
        actionBar?.title = "User's posts"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val postsListView = findViewById<ListView>(R.id.list)
        var postsList: ArrayList<Post> = arrayListOf()
        var adapter: ArrayAdapter<Post>?
        val id = intent.getIntExtra("id", 0)

        val thread = Thread {
            run {
                postsList = getPosts(id)
            }
            runOnUiThread {
                adapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, postsList)
                postsListView.adapter = adapter
            }
        }
        thread.start()

        postsListView.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("id", postsList[i].id)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }

}