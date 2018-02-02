package com.danielbostwick.catfacts.api.data

import com.fasterxml.jackson.annotation.JsonProperty

data class CatFact(@JsonProperty("id") val id: Int,
                   @JsonProperty("content") val content: String,
                   @JsonProperty("links") val links: List<CatFactLink>)