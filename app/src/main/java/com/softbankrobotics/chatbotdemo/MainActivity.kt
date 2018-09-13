package com.softbankrobotics.chatbotdemo

import android.os.Bundle
import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.builder.TopicBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.softbankrobotics.qisdkutils.ui.conversation.ConversationItemType
import com.softbankrobotics.qisdkutils.ui.conversation.ConversationView

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    private var chat: Chat? = null
    private var qiContext: QiContext? = null
    private var conversationView: ConversationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conversationView = findViewById(R.id.conversationView)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "... asking for register ...")
        QiSDK.register(this, this)
        Log.d(TAG, "... registered.")
    }

    override fun onRobotFocusGained(theContext: QiContext) {
        qiContext = theContext
        Log.d(TAG, "runChat()")
        // Create a QiChatbot
        val say = SayBuilder.with(qiContext)
                .withText("Getting ready to chat")
                .build()
        say.run()
        displayLine("Ready to chat.", ConversationItemType.ROBOT_OUTPUT)
        val topic = TopicBuilder.with(qiContext)
                .withResource(R.raw.demo_qichat)
                .build()
        // Create a new QiChatbot.
        Log.i("TAG", "Create qichatbot")
        val qiChatbot = QiChatbotBuilder.with(qiContext)
                .withTopic(topic)
                .build()
        // Create a new Chatbot to handle Azure chatbot
        if (this.qiContext != null) {
            //val azureChatbot = AzureChatbot(this.qiContext)
            val qnAChatbot = QnAChatbot(this.qiContext)
            // Create a new Chat action.
            Log.i("TAG", "Create chat action")
            chat = ChatBuilder.with(qiContext)
                    //.withChatbot(azureChatbot)
                    .withChatbot(qiChatbot, qnAChatbot)
                    .build()
            // Plug listeners for nice display
            chat?.addOnHeardListener { phrase ->
                displayLine(phrase.text, ConversationItemType.HUMAN_INPUT)
            }
            chat?.addOnSayingChangedListener { phrase ->
                displayLine(phrase.text, ConversationItemType.ROBOT_OUTPUT)
            }
            // run it!
            chat?.async()?.run()
            Log.i("TAG", "Async run started...")
        }
    }

    private fun displayLine(text: String, type: ConversationItemType) {
        Log.i(TAG, "Displaying phrase: $text")
        runOnUiThread { conversationView?.addLine(text, type) }
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
