package ru.davidzh.artifactsproject.model

import org.openapitools.client.models.MonsterSchema

data class MonsterRepository(
    val monsters: Map<String, MonsterSchema>
)
