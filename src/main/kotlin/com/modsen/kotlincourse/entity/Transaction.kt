package com.modsen.kotlincourse.entity

import com.modsen.kotlincourse.entity.enums.Currency
import java.math.BigDecimal
import java.time.Instant
import java.util.*

open class Transaction(
        val id: UUID,
        val date: Instant,
        val initiator: User,
        val fromCurrency: Currency,
        val fromAmount: BigDecimal,
) {
        constructor(initiator: User, fromCurrency: Currency, fromAmount: BigDecimal) : this(
                UUID.randomUUID(),
                Instant.now(),
                initiator,
                fromCurrency,
                fromAmount
        )
}