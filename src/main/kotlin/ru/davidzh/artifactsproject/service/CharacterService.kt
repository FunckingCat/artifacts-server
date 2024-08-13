package ru.davidzh.artifactsproject.service

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import org.openapitools.client.apis.CharactersApi
import org.openapitools.client.models.AddCharacterSchema
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.davidzh.artifactsproject.model.GameCharacter

@Service
class CharacterService(
    private val gameCharacterChannel: Channel<GameCharacter>,
    private val charactersApi: CharactersApi,
    private val turnService: TurnService,

) {

    companion object {
        private val log = LoggerFactory.getLogger(GameLoopService::class.java)
    }

    suspend fun makeTurn(character: GameCharacter) {
        log.info("Character {} turn", character.name)
        createCharacterIfNotExists(character)


        val characterCoolDown = turnService.turn(character)
        log.info("Character {} sleep {} before send to channel", character.name, characterCoolDown)
        delay(characterCoolDown)
        gameCharacterChannel.send(character)
    }

    suspend fun createCharacterIfNotExists(character: GameCharacter) {

        if (character.exists) {
            return
        }

        val remoteCharacter = charactersApi.getCharacterCharactersNameGet(character.name)

        if (remoteCharacter.isSuccessful) {
            log.info("Character {} already exists", character.name)
            character.exists = true
            return
        }

        val createCharacterResult = charactersApi.createCharacterCharactersCreatePost(AddCharacterSchema(
            name = character.name,
            skin = AddCharacterSchema.Skin.valueOf(character.skin.name)
        ))

        if (createCharacterResult.isSuccessful) {
            log.info("Character {} created", character.name)
            character.exists = true
        } else {
            log.warn(
                "Character registration {} failed: {} {}",
                character.name,
                createCharacterResult.code(),
                createCharacterResult.message()
            )
        }

    }

}