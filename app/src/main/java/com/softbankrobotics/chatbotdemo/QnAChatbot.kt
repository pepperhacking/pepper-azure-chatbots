package com.softbankrobotics.chatbotdemo

import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.*
import com.aldebaran.qi.sdk.`object`.locale.Locale
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.OkHttpClient
import java.io.IOException

const val QNA_HOST = "https://pepperqna.azurewebsites.net/qnamaker"

class QnAChatbot internal constructor(context: QiContext?) : BaseChatbot(context) {
    val JSON = MediaType.parse("application/json; charset=utf-8")

    var client = OkHttpClient()

    @Throws(IOException::class)
    fun post(url: String, json: String): String? {
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
                .addHeader("Authorization", "EndpointKey $QNA_ENDPOINT_KEY")
                .url(url)
                .post(body)
                .build()
        val response = client.newCall(request).execute()
        return response.body()?.string()
    }

    override fun replyTo(phrase: Phrase, locale: Locale): StandardReplyReaction? {
        if (phrase.text.isNotEmpty()){


            val url = QNA_HOST + QNA_ENDPOINT;
            val json = "{\"question\":\"$phrase.text\"}"
            val response = post(url, json)

            // Return a reply built from the agent's response
            if (response != null) {
                return replyFromAIResponse(response)
            }
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