package com.notes.notesmanager

data class DbDataDetails(val details: Any?)
data class Results(val code: Int, val details: Any?)
data class DbResults(
    val count: Int?,
    val currentPage:Int?,
    val details: Any?)
data class DbActivity(
    val title:String?,
    val status: String?,
    val project:String?,
    val content:String?,
    val dueDate:String?,
    val flagged:Boolean,
    val actors:List<String>,
)
