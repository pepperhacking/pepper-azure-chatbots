package com.softbankrobotics.chatbotdemo

import android.os.AsyncTask
import android.util.Log
import com.aldebaran.qi.Promise
import com.smartnsoft.directlinechatbot.DirectLineChatbot

typealias AIRequest = String
typealias AIResponse = String

class RequestTask internal constructor(private val directLineBot : DirectLineChatbot) : AsyncTask<AIRequest, Void, AIResponse>() {
    private val responsePromise = Promise<AIResponse>()
    companion object {
        private val TAG = "RequestTask"
    }
    /**
     * Get synchronously the response of the request.
     * @return the result of the request
     */
    internal
    val response: AIResponse
        get() = responsePromise.future.value

    override fun doInBackground(vararg requests: AIRequest): AIResponse? {
        val request = requests[0]
        //var result: AIResponse? = null

        // Calling "start" each time may be inefficient...
        directLineBot.start(object : DirectLineChatbot.Callback {
            override fun onStarted() {
                Log.d("CHATBOT", "Started")
                directLineBot.send(request)
            }

            override fun onMessageReceived(message: String) {
                //Log.d("CHATBOT", message)
                responsePromise.setValue(message)
            }
        })

        return responsePromise.future.value

            /*
        try {
            result = aiDataService.request(request)
        } catch (e: Exception) {
            Log.e(TAG, "aiDataService request error", e)
        }

        // By setting the promise we unlock any waiting call on answerTo()
        responsePromise.setValue(result)
        return result
        */
    }

}
