package com.backend.plugins

import com.backend.repository.HeroRepository
import com.backend.repository.HeroRepositoryAlternative
import com.backend.routes.getAllHeroes
import com.backend.routes.getAllHeroesAlternative
import com.backend.routes.root
import com.backend.routes.searchHeroes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val heroRepository: HeroRepository by inject()
    //val heroRepositoryAlternative: HeroRepositoryAlternative by inject()

    routing {
        root()
        getAllHeroes(heroRepository)
        //getAllHeroesAlternative(heroRepositoryAlternative)
        searchHeroes(heroRepository)

        staticResources(remotePath = "/images", basePackage = "images")
    }
}