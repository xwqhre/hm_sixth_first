package com.example.hm_sixth_first

data class Model (
    val title: String,
    var next: Boolean = false){

    fun done(){
        next = !next
    }

}

