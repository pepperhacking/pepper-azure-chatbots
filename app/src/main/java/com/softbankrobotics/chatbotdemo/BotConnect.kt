package com.softbankrobotics.chatbotdemo

import android.util.Log
import com.smartnsoft.directlinechatbot.DirectLineChatbot


val TAG = "ChatbotDemo"

class BotConnect private constructor() {
    class DialogflowConnect private constructor() {
        //private val aiDataService: AIDataService
        //private val
        private val chatbot : DirectLineChatbot

        init {
            Log.d("initTag", "this is a singleton")
            // The access token is a
            //val config = AIConfiguration("YOUR_CLIENT_ACCESS_TOKEN", AIConfiguration.SupportedLanguages.English)
            //aiDataService = AIDataService(config)
            chatbot = DirectLineChatbot("YOUR_DIRECTLINE_SECRET")
            chatbot.start(object : DirectLineChatbot.Callback {
                override fun onStarted() {
                    Log.d("CHATBOT", "Started")
                    chatbot.send("Hello, Bot!")
                }

                override fun onMessageReceived(message: String) {
                    Log.d("CHATBOT", message)
                }
            })

        }

        companion object {
            private val TAG = "DialogflowConnect"
            //val instance = DialogflowConnect()
        }
    }
}

