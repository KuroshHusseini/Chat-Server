package com.example.kotlin1

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatMessageAdapter(var context: Context, var chatMessages: ArrayList<ChatMessage>) :
    RecyclerView.Adapter<ChatMessageHolder>() {

    override fun onBindViewHolder(holder: ChatMessageHolder, position: Int) {
        var message: ChatMessage = chatMessages[position]
        holder.setDetails(message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false)
        return ChatMessageHolder(view)
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }
}