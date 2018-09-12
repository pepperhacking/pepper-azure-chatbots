A test integration of Microsoft Chatbots in QiSDK, for Pepper.

It demonstrates three kinds of chatbot, all implementing the same BaseChatbot interface:
 * A QiChatbot (defined from a local qichat file)
 * A QnA Chatbot, made with [QnA Maker](https://www.qnamaker.ai/)
 * An [Azure Bot](https://azure.microsoft.com/en-us/services/bot-service/) (deactivated by default)

 The two Azure chatbots require keys, that are in a secrets.kt file not included in this project, you have to create your own, with constants like:


    const val QNA_ENDPOINT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    const val QNA_ENDPOINT_KEY = "XXXXXXXXXXXXXXXXXXXXX"
    const val CHATLINE_SECRET = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

For more documentation on how this works, see [the Android QiSDK documentation](https://android.aldebaran.com/sdk/doc/pepper-sdk/index.html), especially the section [on Chatbots](https://android.aldebaran.com/sdk/doc/pepper-sdk/ch4_api/conversation/reference/chatbot.html).