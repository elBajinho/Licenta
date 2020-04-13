package com.example.mountainair.Model

import com.google.firebase.storage.StorageReference

data class PostToDisplay(val userName : String, val description : String, val photo  : StorageReference )