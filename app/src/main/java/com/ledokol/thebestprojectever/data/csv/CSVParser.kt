package com.ledokol.thebestprojectever.data.csv

import com.ledokol.thebestprojectever.domain.module.User
import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(stream: InputStream): User
}