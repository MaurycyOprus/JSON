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

class TodosActivity : AppCompatActivity() {

    private fun getTodos(id: Int): ArrayList<Todos> {
        val url = "https://jsonplaceholder.typicode.com/todos"
        val todosAll: ArrayList<Todos> = arrayListOf()
        val todos = URL(url).readText()

        try{
            val data = JSONArray(todos)
            for(i in 0 until data.length()){
                val todo: JSONObject = data.getJSONObject(i)
                if(todo.getInt("userId") == id) {
                    todosAll.add(Todos(
                        todo.getString("title"),
                        todo.getBoolean("completed")
                    ))
                }
            }
        } catch(e: JSONException){ }
        return todosAll
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val actionBar = supportActionBar
        actionBar?.title = "User's todos"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra("id", 0)

        val todosList = findViewById<ListView>(R.id.list)
        var adapter: ArrayAdapter<Todos>?
        var todosAll: ArrayList<Todos>

        val thread = Thread {
            run {
                todosAll = getTodos(id)
            }
            runOnUiThread {
                adapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, todosAll)
                todosList.adapter = adapter
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