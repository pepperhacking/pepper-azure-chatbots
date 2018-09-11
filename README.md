A test integration of Microsoft Chatbots in QiSDK.

It demonstrates three kinds of chatbot, all implementing the same BaseChatbot interface:
 * A QiChatbot (defined from a local qichat file)
 * A QnA Chatbot, corresponding to Azure QnA - see qnamaker.ai
 * An Azure Chatbot with DirectLine (deactigated by default)
 
 The two Azure chatbots require keys, that are in a secrets.kt file not included in this project, you have to create your own, with constants like:
 
    const val QNA_ENDPOINT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    const val QNA_ENDPOINT_KEY = "XXXXXXXXXXXXXXXXXXXXX"

    const val CHATLINE_SECRET = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

For now it's still WIP? and doesn't work completely.
