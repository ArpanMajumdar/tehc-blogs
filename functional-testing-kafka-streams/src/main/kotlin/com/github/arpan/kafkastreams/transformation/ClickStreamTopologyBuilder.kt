package com.github.arpan.kafkastreams.transformation

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Produced
import org.slf4j.LoggerFactory

@Serializable
data class ClickStreamDataInput(
    val userId: String,
    val session: String,
    val ip: String,
    val userAgent: String,
    val eventType: String,
    val applicationId: String,
    val url: String
)

@Serializable
data class ClickStreamDataOutput(
    val userId: String,
    val session: String,
    val os: String,
    val platform: String,
    val domain: String
)

object ClickStreamTopologyBuilder {

    private val logger = LoggerFactory.getLogger(ClickStreamTopologyBuilder::class.java)

    fun buildTopology(inputTopic: String, outputTopic: String): Topology {
        val streamsBuilder = StreamsBuilder()
        val clickStream = streamsBuilder.stream<String, String>(inputTopic)
        val transformedClickStream = clickStream
            .mapValues { _, clickDataInput -> Json.decodeFromString<ClickStreamDataInput>(clickDataInput) }
            .selectKey { _, clickDataInput -> clickDataInput.userId }
            .mapValues { _, clickDataInput -> ClickStreamTransformer.transformClickStream(clickDataInput) }
            .mapValues { _, clickDataOutput -> Json.encodeToString(clickDataOutput) }

        transformedClickStream.peek { userId, clickDataOutput ->
            logger.info("key = $userId, value = $clickDataOutput")
        }

        transformedClickStream.to(outputTopic, Produced.with(Serdes.String(), Serdes.String()))

        return streamsBuilder.build()
    }
}