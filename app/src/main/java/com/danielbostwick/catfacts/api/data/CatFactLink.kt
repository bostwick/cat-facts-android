package com.danielbostwick.catfacts.api.data

import com.fasterxml.jackson.annotation.JsonProperty

data class CatFactLink(@JsonProperty("href") val href: String,
                       @JsonProperty("rel") val rel: String)