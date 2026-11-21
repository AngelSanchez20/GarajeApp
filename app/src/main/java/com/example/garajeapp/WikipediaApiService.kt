package com.example.garajeapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WikipediaApiService {

    @GET("api/rest_v1/page/summary/{marca}")
    suspend fun obtenerResumenMarca(
        @Path("marca") marca: String
    ): Response<WikiSummaryResponse>
}
