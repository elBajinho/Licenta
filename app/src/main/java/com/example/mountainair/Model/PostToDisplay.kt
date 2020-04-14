package com.example.mountainair.Model

import com.google.firebase.storage.StorageReference

data class PostToDisplay(val userName : String, var description : String, val photoRef  : StorageReference, val photo : String = "", val userId : String="")
