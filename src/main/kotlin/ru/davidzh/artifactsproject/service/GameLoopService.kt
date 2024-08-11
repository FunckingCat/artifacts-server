package ru.davidzh.artifactsproject.service

import kotlinx.coroutines.runBlocking
import lombok.AllArgsConstructor
import org.openapitools.client.apis.CharactersApi
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class GameLoopService(
    private val characterService: CharacterService,
    private val charactersApi: CharactersApi
) {

    companion object {
        private val log = LoggerFactory.getLogger(GameLoopService::class.java)
    }

    @EventListener(ApplicationReadyEvent::class)
    fun initGameLoop() = runBlocking {
        val result = charactersApi.getCharacterCharactersNameGet("Anotoli")
        log.info("Game loop result: $result")
        log.info("Game loop result: ${result.body()}")
        log.info("Game loop result: ${result.body()?.data?.name}")
    }

}