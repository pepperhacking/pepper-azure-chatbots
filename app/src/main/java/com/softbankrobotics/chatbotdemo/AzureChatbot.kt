package com.softbankrobotics.chatbotdemo

import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.*
import com.aldebaran.qi.sdk.`object`.locale.Locale

class AzureChatbot internal constructor(context: QiContext) : BaseChatbot(context) {
    override fun replyTo(phrase: Phrase, locale: Locale): StandardReplyReaction? {
        if (phrase.text.isNotEmpty()){
            val botConnect = BotConnect.instance
            val aiResponse = botConnect.answerTo(phrase.text)
            // Return a reply built from the agent's response
            return replyFromAIResponse(aiResponse)
        }
        return null
    }

    /**
     * Build a reply that can be processed by our chatbot
     */
    private fun replyFromAIResponse(response: AIResponse): StandardReplyReaction {
        Log.d(TAG, "replyFromAIResponse")
        Log.d(TAG, "answer: $response")
        val reaction: BaseChatbotReaction = ChatbotUtteredReaction(qiContext, response)

        // Make the reply and return it
        return StandardReplyReaction(
                reaction ,
                ReplyPriority.FALLBACK
        )
    }
}