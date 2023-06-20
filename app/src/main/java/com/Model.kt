package com

data class Model (
    val title: String,
    var next: Boolean = false){

    fun done(){
        next = !next
    }

}

