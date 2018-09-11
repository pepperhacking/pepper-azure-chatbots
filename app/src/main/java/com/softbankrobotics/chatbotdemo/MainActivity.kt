package com.softbankrobotics.chatbotdemo

import android.os.Bundle
import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    private var chat: Chat? = null
    private var qiContext: QiContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(theContext: QiContext) {
        qiContext = theContext
        Log.d(TAG, "runChat()")
        // Create a new Chatbot to handle Azure chatbot
        if (this.qiContext != null) {
            //val azureChatbot = AzureChatbot(this.qiContext)
            val qnAChatbot = QnAChatbot(this.qiContext)
            // Create a new Chat action.
            Log.i("TAG", "Create chat action")
            chat = ChatBuilder.with(qiContext)
                    //.withChatbot(azureChatbot)
                    .withChatbot(qnAChatbot)
                    .build()
            chat?.async()?.run()
            Log.i("TAG", "Async run started...")
        }
    }

    override fun onRobotFocusLost() {
        Log.i(TAG, "Focus lost")
    }

    override fun onRobotFocusRefused(reason: String) {
        Log.i(TAG, "Focus refused: $reason")
    }

    fun initInteraction(){
    }
}
