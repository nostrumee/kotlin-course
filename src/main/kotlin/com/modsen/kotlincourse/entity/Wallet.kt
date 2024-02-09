package com.modsen.kotlincourse.entity

import com.modsen.kotlincourse.entity.enums.Currency
import java.math.BigDecimal
import java.util.UUID

class Wallet(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val isCold: Boolean = false,
        val cryptoCurrencies: MutableMap<Currency, BigDecimal> = mutableMapOf(),
        val passphrase: String,
        val user: User,
)
