package com.example.kotlin1

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.json.Json
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        chatMessageArrayList = ArrayList()
        adapter = ChatMessageAdapter(this, chatMessageArrayList)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        sendChatMessage.setOnClickListener {
            val message = editText.text.toString().trim()
            if (message.isNotEmpty()) {
                Thread(ServerWrite(message)).start()
               editText.setText("")
            }
        }
        connectToChatRoom.setOnClickListener {
            Thread(ServerConnect(this)).start()
        }
    }

    companion object {
        lateinit var socket: Socket
        var address = "172.20.10.7"
        var port = 30001
        lateinit var adapter: ChatMessageAdapter
        lateinit var chatMessageArrayList: ArrayList<ChatMessage>

        lateinit var reader: Scanner
        lateinit var writer: OutputStream

        lateinit var serverFeed: Thread

        fun addMessageToRecyclerView(context: Activity, message: String) {
            context.runOnUiThread {
                chatMessageArrayList.add(Json.parse(ChatMessage.serializer(), message))
                adapter.notifyDataSetChanged()
                context.recyclerView.layoutManager!!.scrollToPosition(
                    chatMessageArrayList.size - 1
                )
            }
        }
    }

    class ServerWrite(private var message: String) : Runnable {
        override fun run() {
            write(message)
            writer.flush()
        }

        private fun write(message: String) {
            val messageAsJson =
                Json.stringify(
                    ChatMessage.serializer(),
                    ChatMessage(message, "userName", message, "createdDateAtTime")
                )

            writer.write((messageAsJson + '\n').toByteArray(Charset.defaultCharset()))
        }
    }

    class ServerConnect(private var context: Context) : Runnable {
        override fun run() {
            val newContext = context as Activity

            try {
                socket = Socket(address, port)

                reader = Scanner(socket.getInputStream())
                writer = socket.getOutputStream()

                Log.i("SERVER", "We are connected to ${socket.inetAddress}")
                serverFeed = Thread(ServerFeed(newContext))
                serverFeed.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class ServerFeed(private var context: Activity) : Runnable {
        override fun run() {

            while (true) {
                try {

                    val message: String = reader.nextLine()
                    if (message != null) {
                        addMessageToRecyclerView(context, message)
                    }

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

        }
    }
}