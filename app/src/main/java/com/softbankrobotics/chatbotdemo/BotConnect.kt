package com.softbankrobotics.chatbotdemo

import android.util.Log
import com.smartnsoft.directlinechatbot.DirectLineChatbot

val TAG = "ChatbotDemoBotConnect"

class BotConnect private constructor() {
    private val directLineBot : DirectLineChatbot

    init {
        Log.d(TAG, "Initializing BotConnect singleton")
        directLineBot = DirectLineChatbot("YOUR_DIRECTLINE_SECRET")
        /*
        directLineBot.start(object : DirectLineChatbot.Callback {
            override fun onStarted() {
                Log.d("CHATBOT", "Started")
                directLineBot.send("Hello, Bot!")
            }

            override fun onMessageReceived(message: String) {
                Log.d("CHATBOT", message)
            }
        })
        */
    }

    fun answerTo(question: String): String {
        Log.d(TAG, "answerTo: $question")
        directLineBot.send(question)

        // Execute the request
        val task = RequestTask(directLineBot)
        task.execute(question)

        // Wait for the response and return it
        val response = task.response
        Log.i(TAG, "Response: $response")
        return response
    }

    companion object {
        val instance = BotConnect()
    }
}

