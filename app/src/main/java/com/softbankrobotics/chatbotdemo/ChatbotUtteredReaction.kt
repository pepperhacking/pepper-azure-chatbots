package com.softbankrobotics.chatbotdemo

import android.util.Log
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.BaseChatbotReaction
import com.aldebaran.qi.sdk.`object`.conversation.SpeechEngine
import com.aldebaran.qi.sdk.builder.SayBuilder
import java.util.concurrent.ExecutionException

class ChatbotUtteredReaction internal constructor(qiContext: QiContext, private val toBeSaid: String) : BaseChatbotReaction(qiContext) {
    private var fsay: Future<Void>? = null

    override fun runWith(speechEngine: SpeechEngine) {
        // All Say actions that must be executed inside this method must be created via the SpeechEngine
        val say = SayBuilder.with(speechEngine)
                .withText(toBeSaid)
                .build()

        try {
            // The say action must be executed asynchronously in order to get
            // a future that can be canceled by the head thanks to the stop() method
            fsay = say.async().run()

            // However, runWith must not be leaved before the say action is terminated : thus wait on the future
            fsay?.get()

        } catch (e: ExecutionException) {
            Log.e(TAG, "Error during say", e)
        }

    }

    override fun stop() {

        // All actions created in runWith should be canceled when stop is called

        if (fsay != null) {
            fsay?.requestCancellation()
        }
    }

    companion object {

        private val TAG = "DialogflowChatbotReac"
    }
}
