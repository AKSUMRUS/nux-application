package com.ledokol.thebestprojectever.data.csv

import com.ledokol.thebestprojectever.domain.module.User
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserParser @Inject constructor(

): CSVParser<User> {
    override suspend fun parse(stream: InputStream): User {
        val csvReader = CSVReader(InputStreamReader(stream))
    }
}