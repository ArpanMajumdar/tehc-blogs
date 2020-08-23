package com.github.arpan.kafkastreams

import com.github.arpan.kafkastreams.transformation.ClickStreamDataProcessor
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig
import java.util.*

fun main() {
    val inputTopic = "click-stream-input"
    val outputTopic = "click-stream-output"
    val kafkaProperties = Properties().apply {
        put(StreamsConfig.APPLICATION_ID_CONFIG, "click-stream-app")
        put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String()::class.java)
        put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String()::class.java)
    }

    val clickStreamDataProcessor = ClickStreamDataProcessor(inputTopic, outputTopic, kafkaProperties)
    clickStreamDataProcessor.start()
}