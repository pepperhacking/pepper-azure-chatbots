package com.softbankrobotics.chatbotdemo

import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.*
import com.aldebaran.qi.sdk.`object`.locale.Locale
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.OkHttpClient
import org.json.JSONObject
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
        Log.i(TAG, "Posting question")
        val response = client.newCall(request).execute()
        Log.i(TAG, "Got response")
        return response.body()?.string()
    }

    override fun acknowledgeHeard(phrase: Phrase?, locale: Locale?) {
        Log.i(TAG, "The robot heard: " + phrase?.text)
    }

    override fun acknowledgeSaid(phrase: Phrase?, locale: Locale?) {
        Log.i(TAG, "The robot uttered this reply, provided by another chatbot: " + phrase?.text)
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
    private fun replyFromAIResponse(responseJson: AIResponse): StandardReplyReaction {
        val jObject = JSONObject(responseJson)
        Log.d(TAG, "replyFromAIResponse $responseJson")
        var answer : String = "sorry"
        val answers = jObject.getJSONArray("answers")
        if ((answers != null) && (answers.length() > 0)) {
            val answerm = answers.getJSONObject(0)?.getString("answer")
            Log.d(TAG, "answer: $answerm")
            if (answerm != null) {
                answer = answerm
                if (answer.length > 100) {
                    // Arbitrary cut too long answer, or robot talks for ages.
                    answer = answer.substring(0, 100)
                }
            } else {
                Log.d(TAG, "Weird empty answer")
            }

        } else {
            Log.d(TAG, "json looks malformed or something")
        }
        Log.d(TAG, "About to reply: $answer")
        val reaction: BaseChatbotReaction = ChatbotUtteredReaction(qiContext, answer)
        // Make the reply and return it
        Log.d(TAG, ".. replying...")
        return StandardReplyReaction(
                reaction ,
                ReplyPriority.FALLBACK
        )
    }
}