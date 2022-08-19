package a77777_888.me.t.https.hhcustombasis.source

import a77777_888.me.t.https.hhcustombasis.model.entities.areas.Areas
import a77777_888.me.t.https.hhcustombasis.model.entities.areas.startWith
import a77777_888.me.t.https.hhcustombasis.model.entities.employer.EmployerResponseEntity
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancies.VacanciesResponseEntity
import a77777_888.me.t.https.hhcustombasis.model.entities.vacancy.VacancyResponseEntity
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

@RunWith(JUnit4::class)
class RetrofitSourceTest {

    private lateinit var mockWebServer: MockWebServer
//    private lateinit var service: HhAPI
    private lateinit var service: HeadHunterAPI

//    @Before
//    fun createTestService() {
//        mockWebServer = MockWebServer()
//        service = Retrofit.Builder()
//            .baseUrl(mockWebServer.url("/"))
////            .client(okHttpClient())
//
////            .addConverterFactory(
////                MoshiConverterFactory.create(
////                    Moshi.Builder()
//////                        .add(KotlinJsonAdapterFactory())
////                        .build()
////                )
////            )
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(HhAPI::class.java)
//    }

    @Before
    fun createServiceFromApp() {
        mockWebServer = MockWebServer()
        service = RetrofitSource.retrofit
            .create(HeadHunterAPI::class.java)
    }

    @After
    fun stopServer() = mockWebServer.shutdown()

    @Test
    fun getArea97() = runBlocking{
        enqueueResponse("area_97_response.json")
        val area = service.getArea("97")

//        println("Test.getArea97: ${area.id}\n${area.parent_id}\n${area.name}\n${area.areas}")

        assertEquals(area.id, "97")
        assertEquals(area.name, "Узбекистан")
    }

    @Test
    fun getAres() = runBlocking{
        enqueueResponse("areas_response.json")
        val areas = service.getAreas()

//        testAreasStartWith(areas)

//        areas.forEach {
//            println("${it.id}     ${it.name}")
//        }
//        println()
//        println("areas[0]:\nid = ${areas[0].id}\nname = ${areas[0].name}\nparent_id = ${areas[0].parent_id}")

        assert(areas.any { it.id == "16" })
        assert(areas.any { it.name == "Россия" })
    }

    private fun testAreasStartWith(areas: List<Areas>) {
        println("testAreasStartWith:")
        println(areas.startWith("М", 3))
        areas.startWith("Р", 7).forEach (::println)

    }

    @Test
    fun getVacancies() = runBlocking {
        enqueueResponse("vacancies_response.json")
        val vacanciesResponseEntity = service.getVacancies()

        with(vacanciesResponseEntity) {
//            println(items[1])
//            println("found: $found")
//            println("pages: $pages")
//            println("page: $page")
//            println("clusters: $clusters")

//            assertEquals(items.find { it.id == "67487031"}!!.area.name, "Калининград")
//            assertEquals(found, 5)
            assertEquals(page, 0)
            assertEquals(per_page, 20)
            assertEquals(clusters, null)
        }

    }

    @Test
    fun getVacancy() = runBlocking {
        enqueueResponse("vacancy_response.json")
        val vacancyResponseEntity = service.getVacancy("67487031")
        with(vacancyResponseEntity) {
//            println("Vacancy:")
//            println("id = $id")
//            println("name = $name")

            assertEquals(id, "67487031")
            assertEquals(name, "Android разработчик")
        }
    }

    @Test
    fun getEmployer() = runBlocking {
        enqueueResponse("employer_response.json")
        val employerResponseEntity = service.getEmployer("3333270")
        with(employerResponseEntity) {
//            println("Employer")
//            println("id = $id")
//            println("name = $name")

            assertEquals(id, "3333270")
            assertEquals(name, "Вуду Рокс")
        }
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()

        val mockResponse = MockResponse()
//        mockResponse.headers = responseHeaders
        val response = mockResponse.setBody(source.readString(Charsets.UTF_8))

//        println("mockResponse.body: ${mockResponse.getBody()}")

        mockWebServer.enqueue(
            response
        )
    }

//    private fun okHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .build()
//    }

    private interface HhAPI {

        @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
        @GET("/areas")
        suspend fun getAreas(): List<Areas>

        @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
        @GET("/areas/{id}")
        suspend fun getArea(@Path("id") id: String): Areas

        @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
        @GET("/vacancies")
        suspend fun getVacancies(): VacanciesResponseEntity

        @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
        @GET("/vacancies/{id}")
        suspend fun getVacancy(@Path("id") id: String): VacancyResponseEntity

        @Headers("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0)")
        @GET("/employers/{id}")
        suspend fun getEmployer(@Path("id") id: String): EmployerResponseEntity
    }

//    @JsonClass(generateAdapter = true)
//    data class TestArea(
//        var areas: ArrayList<TestArea>,
//        var id: String,
//        var name: String,
//        var parent_id: String?
//    )

//    private val responseHeaders = mapOf(
//        "X-Firefox-Spdy" to "h2",
//        "access-control-allow-origin" to "*",
//        "cache-control" to "max-age=1200",
//        "content-encoding" to "gzip",
//        "content-type" to "application/json; charset=UTF-8",
//        "date" to "Sat, 30 Jul 2022 10:21:23 GMT",
////        "etag" to "W/""0079b25c2ee0b90bdf9698bb8d5c53404"-ai-812ed4562d3211363a7b813aa9cd2cf042b63bb2-RU66eb9f75d25d9f52b549459e85e44d479a176927"",
//        "expires" to "Sat, 30 Jul 2022 10:41:23 GMT",
////        "nel" to "{"success_fraction":0,"report_to":"nel","max_age":3600}",
////        "report-to" to "{"group":"nel","endpoints":[{"url":"https:\/\/nel.hhdev.ru\/report\/api"}],"max_age":3600}",
//        "server" to "ddos-guard",
//        "vary" to "Accept-Encoding",
//        "x-content-type-options" to "nosniff",
//        "x-frame-options" to "SAMEORIGIN",
//        "x-request-id" to "16591764838012dc13a713e761bee463, 16591764838012dc13a713e761bee463",
//    ).toHeaders()

}