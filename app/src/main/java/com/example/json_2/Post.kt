package com.example.json_2

class Post(val id: Int, private val title: String, private val body: String) {

    override fun toString(): String {
        return "\n" + this.title + "\n\n" + this.body + "\n"
    }
}