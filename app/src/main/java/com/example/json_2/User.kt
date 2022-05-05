package com.example.json_2

import java.io.Serializable

class User(id: Int, full_name: String, email: String, finishedTodosNum: Int = 0, allTodosNum: Int = 0, postNum: Int = 0) : Serializable{
    var id: Int? = id
    var fullName: String? = full_name
    var email: String? = email
    var postNum: Int? = postNum
    var allTodosNum: Int? = allTodosNum
    var finishedTodosNum: Int? = finishedTodosNum

    override fun toString(): String {
        return  "Full name: " + this.fullName!! + "\n" +
                "E-mail: " + this.email!! + "\n" +
                "Number of posts: " + this.postNum.toString() + "\n" +
                "Todos: " + this.finishedTodosNum.toString() + " / " + this.allTodosNum.toString()
    }

    fun addPost(){
        this.postNum = this.postNum?.plus(1)
    }

    fun addTodo(){
        this.allTodosNum = this.allTodosNum?.plus(1)
    }

    fun addTodoDone(){
        this.finishedTodosNum = this.finishedTodosNum?.plus(1)
    }

}