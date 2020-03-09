package com.example.kotlin1

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userName: TextView
    private val messageTxt: TextView
    private val dateTxt: TextView

    init {
        userName = itemView.findViewById(R.id.userTextView)
        messageTxt = itemView.findViewById(R.id.messageTextView)
        dateTxt = itemView.findViewById(R.id.dateTextView)
    }

    fun setDetails(message: ChatMessage) {
        userName.text = message.user
        messageTxt.text = message.message
        dateTxt.text = message.createDateTime
    }
}
