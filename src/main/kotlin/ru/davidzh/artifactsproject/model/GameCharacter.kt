package ru.davidzh.artifactsproject.model

import org.openapitools.client.models.CharacterSchema

data class GameCharacter(
    val name: String,
    val skin: CharacterSchema.Skin,
) {
    var exists: Boolean = false
}
