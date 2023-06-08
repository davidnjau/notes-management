package com.notes.notesmanager

import org.springframework.http.ResponseEntity
import java.util.Date

class FormatterClass {

    fun getDate(date:String):Date?{

        return null
    }
    fun getResponse(results: Results): ResponseEntity<*>? {
        return when (results.code) {
            200, 201 -> {
                ResponseEntity.ok(results.details)
            }
            500 -> {
                ResponseEntity.internalServerError().body(results)
            }
            else -> {
                ResponseEntity.badRequest().body(DbDataDetails(results.details.toString()))
            }
        }
    }

}