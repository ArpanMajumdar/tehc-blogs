package com.github.arpan.kafkastreams.transformation

import org.apache.kafka.streams.KafkaStreams
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess


class ClickStreamDataProcessor(
    private val inputTopic: String,
    private val outputTopic: String,
    private val kafkaProperties: Properties
) {
    private val logger = LoggerFactory.getLogger(ClickStreamDataProcessor::class.java)

    fun start() {
        val topology = ClickStreamTopologyBuilder.buildTopology(inputTopic, outputTopic)
        val kafkaStreams = KafkaStreams(topology, kafkaProperties)

        // Shutdown hook
        val countDownLatch = CountDownLatch(1)
        Runtime.getRuntime().addShutdownHook(Thread
        {
            kafkaStreams.close()
            countDownLatch.countDown()
        })

        // Start kafka streams
        try {
            kafkaStreams.start()
            countDownLatch.await()
        } catch (ex: Exception) {
            logger.error("Error occurred while starting streams app", ex)
            exitProcess(-1)
        }
    }
}