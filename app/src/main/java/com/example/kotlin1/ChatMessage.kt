package com.example.kotlin1

import kotlinx.serialization.Serializable

//For date and time
// For users name
//For command line
//For user messages
@Serializable
class ChatMessage(
    val message: String?,
    val user: String?,
    val command: String?,
    val createDateTime: String?
)