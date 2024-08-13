package ru.davidzh.artifactsproject.config

import kotlinx.coroutines.channels.Channel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.davidzh.artifactsproject.model.GameCharacter

@Configuration
class ChannelConfig {

    @Bean
    fun getGameCharacterChannel(): Channel<GameCharacter> {
        return Channel(Channel.UNLIMITED)
    }

}