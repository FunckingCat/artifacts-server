package ru.davidzh.artifactsproject.service

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lombok.AllArgsConstructor
import org.openapitools.client.apis.CharactersApi
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.davidzh.artifactsproject.model.CharacterRef
import ru.davidzh.artifactsproject.model.GameCharacter
import ru.davidzh.artifactsproject.model.MonsterRepository
import java.util.*

@Service
@AllArgsConstructor
class GameLoopService(
    private val monsterRepository: MonsterRepository,
    private val characterService: CharacterService,
    private val charactersApi: CharactersApi,
    private val gameCharacterChannel: Channel<GameCharacter>,
) {

    companion object {
        private val log = LoggerFactory.getLogger(GameLoopService::class.java)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @EventListener(ApplicationReadyEvent::class)
    fun gameLoop() = runBlocking {
        launch {

            val gameCharacter = Arrays.stream(CharacterRef.entries.toTypedArray())
                .map { characterRef -> GameCharacter(name = characterRef.characterName, skin = characterRef.skin) }
                .toList()

            for (character : GameCharacter in gameCharacter) {
                log.info("Send character {}", character)
                gameCharacterChannel.send(character)
            }

            while (!gameCharacterChannel.isClosedForReceive) {
                log.info("Receive element from channel")
                val character = gameCharacterChannel.receive()
                characterService.makeTurn(character)
            }

        }
    }

}