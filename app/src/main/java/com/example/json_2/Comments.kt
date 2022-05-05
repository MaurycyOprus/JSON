package com.example.json_2

class Comments(val name: String, private val email: String, private val body: String) {

    override fun toString(): String {
        return "\n" + this.email + "\n" + this.name + "\n\n" + this.body + "\n"
    }

}