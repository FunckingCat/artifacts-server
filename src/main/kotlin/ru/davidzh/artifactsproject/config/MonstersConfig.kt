package ru.davidzh.artifactsproject.config

import kotlinx.coroutines.runBlocking
import org.openapitools.client.apis.MonstersApi
import org.openapitools.client.models.MonsterSchema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.davidzh.artifactsproject.model.MonsterRepository

@Configuration
class MonstersConfig(
    private val monstersApi: MonstersApi
) {

    @Bean
    fun getMonstersRepository(monstersApi: MonstersApi): MonsterRepository {
        val monstersList: List<MonsterSchema> = runBlocking {
            monstersApi.getAllMonstersMonstersGet().body()?.data?.toList().orEmpty()
        }
        val map: Map<String, MonsterSchema> = monstersList.associateBy({ monsterSchema: MonsterSchema -> monsterSchema.code }, { m -> m})
        return MonsterRepository(map)
    }

}