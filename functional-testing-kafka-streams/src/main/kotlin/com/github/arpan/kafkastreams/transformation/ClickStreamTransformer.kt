package com.github.arpan.kafkastreams.transformation

object ClickStreamTransformer {
    fun transformClickStream(clickStreamDataInput: ClickStreamDataInput): ClickStreamDataOutput =
        ClickStreamDataOutput(
            userId = clickStreamDataInput.userId,
            session = clickStreamDataInput.session,
            os = getOsFromUserAgent(clickStreamDataInput.userAgent),
            platform = getPlatformFromUserAgent(clickStreamDataInput.userAgent),
            domain = extractDomainFromUrl(clickStreamDataInput.url)
        )

    private fun getOsFromUserAgent(userAgent: String): String {
        return ""
    }

    private fun getPlatformFromUserAgent(userAgent: String): String {
        return ""
    }

    private fun extractDomainFromUrl(url: String): String {
        return ""
    }
}