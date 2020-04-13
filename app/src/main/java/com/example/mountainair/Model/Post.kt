package com.example.mountainair.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(val userId : String, val description : String, val photo  : String ) {

//    @Exclude
//    fun toArray(): ArrayList<Post> {
//        return arrayOf(
//            "userId" to userId,
//            "description" to description,
//            "photo" to photo
//        )
//    }
}