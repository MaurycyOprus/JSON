package com.example.json_2

import androidx.core.graphics.toColorInt

class Todos(private val title: String, private val completed: Boolean) {

    override fun toString(): String {
        val status: String = if(this.completed) "completed" else "not completed"
        return  this.title + "\n" + "Status: " + status + "\n"
    }

}