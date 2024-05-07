package com.blogspot.softwareengineerrohan.naarirakshak.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
@PrimaryKey(autoGenerate = true)
    val id : Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "Relation")
    val relation: String,
    @ColumnInfo(name = "number")
    val number: String



)