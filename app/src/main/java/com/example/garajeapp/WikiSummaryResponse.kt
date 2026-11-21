package com.example.garajeapp

import com.google.gson.annotations.SerializedName

data class WikiSummaryResponse(
    @SerializedName("title")
    val title: String?,

    @SerializedName("extract")
    val extract: String?,

    @SerializedName("thumbnail")
    val thumbnail: WikiThumbnail?
)

data class WikiThumbnail(
    @SerializedName("source")
    val source: String?
)
