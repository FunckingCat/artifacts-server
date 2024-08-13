package ru.davidzh.artifactsproject.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.davidzh.artifactsproject.model.GameCharacter
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@Service
class TurnService {

    companion object {
        private val log = LoggerFactory.getLogger(GameLoopService::class.java)
    }

    @OptIn(ExperimentalTime::class)
    suspend fun turn(character: GameCharacter): Duration {
        log.info("Character {} makes turn", character.name)
        return Duration.convert(1.0, DurationUnit.SECONDS, DurationUnit.MILLISECONDS).toDuration(DurationUnit.MILLISECONDS)
    }

}