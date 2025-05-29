package com.backend

import com.backend.models.ApiResponse
import com.backend.repository.HeroRepositoryImpl
import com.backend.repository.NEXT_PAGE_KEY
import com.backend.repository.PREVIOUS_PAGE_KEY
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    private fun TestApplicationBuilder.setupApplication() {
        application {
            module()
        }
    }

    @Test
    fun `access root endpoint, assert correct information`() = testApplication {
        setupApplication()
        val response = client.get("/")
        assertEquals(
            expected = HttpStatusCode.OK,
            actual = response.status
        )
        assertEquals(
            expected = "Welcome to Boruto API!",
            actual = response.bodyAsText()
        )
    }

    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() = testApplication {
        setupApplication()
        val heroRepository = HeroRepositoryImpl()
        val pages = 1..5
        val heroes = listOf(
            heroRepository.page1,
            heroRepository.page2,
            heroRepository.page3,
            heroRepository.page4,
            heroRepository.page5
        )
        pages.forEach { page ->
            val response = client.get("/boruto/heroes?page=$page")
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = response.status
            )
            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
            val expected = ApiResponse(
                success = true,
                message = "ok",
                prevPage = calculatePage(page = page)["prevPage"],
                nextPage = calculatePage(page = page)["nextPage"],
                heroes = heroes[page - 1],
                lastUpdated = actual.lastUpdated
            )
            assertEquals(
                expected = expected,
                actual = actual
            )
        }
    }

    @Test
    fun `access all heroes endpoint, query non existing page number, assert error`() = testApplication {
        setupApplication()
        val response = client.get("/boruto/heroes?page=6")
        assertEquals(
            expected = HttpStatusCode.NotFound,
            actual = response.status
        )
        assertEquals(
            expected = "Page not Found",
            actual = response.bodyAsText()
        )
    }

    @Test
    fun `access all heroes endpoint, query invalid page number, assert error`() = testApplication {
        setupApplication()
        val response = client.get("/boruto/heroes?page=invalid")
        assertEquals(
            expected = HttpStatusCode.BadRequest,
            actual = response.status
        )
        val expected = ApiResponse(
            success = false,
            message = "Only Numbers Allowed"
        )
        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
        assertEquals(
            expected = expected,
            actual = actual
        )
    }

    @Test
    fun `access search heroes endpoint, query hero name, assert single hero result`() = testApplication {
        setupApplication()
        val response = client.get("/boruto/heroes/search?name=sas")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes.size
        assertEquals(expected = 1, actual = actual)
    }

    @Test
    fun `access search heroes endpoint, query hero name, assert multiple heroes result`() = testApplication {
        setupApplication()
        val response = client.get("/boruto/heroes/search?name=sa")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes.size
        assertEquals(expected = 3, actual = actual)
    }

    @Test
    fun `access search heroes endpoint, query an empty text, assert empty list as a result`() = testApplication {
        setupApplication()
        val response = client.get("/boruto/heroes/search?name=")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes
        assertEquals(expected = emptyList(), actual = actual)
    }

    @Test
    fun `access search heroes endpoint, query non existing hero, assert empty list as a result`() = testApplication {
        setupApplication()
        val response = client.get("/boruto/heroes/search?name=unknown")
        assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes
        assertEquals(expected = emptyList(), actual = actual)
    }

    @Test
    fun `access non existing endpoint, assert not found`() = testApplication {
        setupApplication()
        val response = client.get("/unknown")
        assertEquals(expected = HttpStatusCode.NotFound, actual = response.status)
        assertEquals(expected = "Page not Found", actual = response.bodyAsText())
    }

    private fun calculatePage(page: Int) =
        mapOf(
            PREVIOUS_PAGE_KEY to if (page in 2..5) page.minus(1) else null,
            NEXT_PAGE_KEY to if (page in 1..4) page.plus(1) else null
        )

}