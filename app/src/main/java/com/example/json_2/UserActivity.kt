package com.example.json_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.json_2.MainActivity.Companion.getUserIndex
import com.example.json_2.MainActivity.Companion.getUsersFromURL

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val actionBar = supportActionBar
        actionBar?.title = "Profile of user"

        val showTasksButton = findViewById<Button>(R.id.showTasks_button)
        val showPostsButton = findViewById<Button>(R.id.showPosts_button)
        val emailVal = findViewById<TextView>(R.id.email_val)
        val finishedTasks = findViewById<TextView>(R.id.finishedTasks)
        val nbOfPosts = findViewById<TextView>(R.id.nbOfPosts)
        val allTasks = findViewById<TextView>(R.id.allTasks)
        val fullName = findViewById<TextView>(R.id.full_name)


        val userId: Int = intent.getIntExtra("userId", 0)
        var user: User? = null

        val thread = Thread{
            run{
                val usersAll = getUsersFromURL()
                user = usersAll[getUserIndex(usersAll, userId)]
            }

            runOnUiThread {
                fullName.text = user?.fullName
                emailVal.text = user?.email
                nbOfPosts.text = user?.postNum.toString()
                allTasks.text = user?.allTodosNum.toString()
                finishedTasks.text = user?.finishedTodosNum.toString()
            }
        }

        thread.start()

        showPostsButton.setOnClickListener{
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("id", user?.id)
            startActivity(intent)
        }

        showTasksButton.setOnClickListener{
            val intent = Intent(this, TodosActivity::class.java)
            intent.putExtra("id", user?.id)
            startActivity(intent)
        }


    }

}