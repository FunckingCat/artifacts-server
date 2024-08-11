package ru.davidzh.artifactsproject.model

import lombok.Getter
import org.openapitools.client.models.CharacterSchema

enum class Character(
    @Getter val characterName: String,
    @Getter val skin: CharacterSchema.Skin
) {

    ANATOLI("Anatoli", CharacterSchema.Skin.men1),
    IVAN("Ivan", CharacterSchema.Skin.men2),
    GOSHAN("Goshan", CharacterSchema.Skin.men3),
    LYUDA("Lyuda", CharacterSchema.Skin.women1),
    MASHA("Masha", CharacterSchema.Skin.women2);

}