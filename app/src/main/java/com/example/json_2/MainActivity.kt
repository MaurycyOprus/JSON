package com.example.json_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    companion object{
        fun getUserIndex(arr: ArrayList<User>, id: Int): Int{
            for(i in 0 until arr.size){
                if (arr[i].id == id) return i
            }
            return 0
        }

        fun getUsersFromURL(): ArrayList<User>{
            val usersURL = "https://jsonplaceholder.typicode.com/users"
            val postsURL = "https://jsonplaceholder.typicode.com/posts"
            val todosURL = "https://jsonplaceholder.typicode.com/todos"

            val usersAll: ArrayList<User> = arrayListOf()

            val users = URL(usersURL).readText()
            val posts = URL(postsURL).readText()
            val todos = URL(todosURL).readText()

            try{
                val data = JSONArray(users)
                for(i in 0 until data.length()){
                    val user: JSONObject = data.getJSONObject(i)
                    usersAll.add(User(
                        user.getInt("id"),
                        user.getString("name"),
                        user.getString("email")
                    ))
                }
            } catch(e: JSONException){ }
            try{
                val data = JSONArray(posts)
                for(i in 0 until data.length()){
                    val post: JSONObject = data.getJSONObject(i)
                    usersAll[getUserIndex(usersAll, post.getInt("userId"))].addPost()
                }
            } catch(e: JSONException){ }
            try{
                val data = JSONArray(todos)
                for(i in 0 until data.length()){
                    val todo: JSONObject = data.getJSONObject(i)
                    usersAll[getUserIndex(usersAll, todo.getInt("userId"))].addTodo()

                    if(todo.getBoolean("completed")){
                        usersAll[getUserIndex(usersAll, todo.getInt("userId"))].addTodoDone()
                    }
                }
            } catch(e: JSONException){ }
            return usersAll
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val actionBar = supportActionBar
        actionBar?.title = "All users"

        val userList = findViewById<ListView>(R.id.list)
        var adapter: ArrayAdapter<User>?
        var usersAll: ArrayList<User> = arrayListOf()

        val thread = Thread {
            run {
                usersAll = getUsersFromURL()
            }
            runOnUiThread {
                adapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, usersAll)
                userList.adapter = adapter
            }
        }
        thread.start()

        userList.setOnItemClickListener { _, _, pos, _ ->
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("userId", usersAll[pos].id)
            startActivity(intent)
        }
    }
}