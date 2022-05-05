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

class CommentsActivity  : AppCompatActivity() {

    private fun getComments(id: Int): ArrayList<Comments> {
        val url = "https://jsonplaceholder.typicode.com/comments"
        val commentsAll: ArrayList<Comments> = arrayListOf()
        val comments = URL(url).readText()

        try{
            val data = JSONArray(comments)
            for(i in 0 until data.length()){
                val comment: JSONObject = data.getJSONObject(i)
                if(comment.getInt("postId") == id) {
                    commentsAll.add(Comments(
                        comment.getString("name"),
                        comment.getString("email"),
                        comment.getString("body")
                    ))
                }
            }
        } catch(e: JSONException){ }
        return commentsAll
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val actionBar = supportActionBar
        actionBar?.title = "Comments"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val commentListView = findViewById<ListView>(R.id.list)
        var commentsList: ArrayList<Comments>
        var adapter: ArrayAdapter<Comments>?
        val id = intent.getIntExtra("id", 0)

        val thread = Thread {
            run {
                commentsList = getComments(id)
            }
            runOnUiThread {
                adapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, commentsList)
                commentListView.adapter = adapter
            }
        }
        thread.start()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }
}