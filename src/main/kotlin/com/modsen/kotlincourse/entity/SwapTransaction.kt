package com.modsen.kotlincourse.entity

import com.modsen.kotlincourse.entity.enums.Currency
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class SwapTransaction(
    id: UUID,
    date: Instant,
    initiator: User,
    fromCurrency: Currency,
    fromAmount: BigDecimal,
    val toCurrency: Currency,
    val toAmount: BigDecimal,
) : Transaction(id, date, initiator, fromCurrency, fromAmount) {
    constructor(
        initiator: User,
        fromCurrency: Currency,
        fromAmount: BigDecimal,
        toCurrency: Currency,
        toAmount: BigDecimal
    ) : this(
        UUID.randomUUID(),
        Instant.now(),
        initiator,
        fromCurrency,
        fromAmount,
        toCurrency,
        toAmount
    )

    override fun toString(): String {
        return "SwapTransaction(date=$date, initiator=$initiator, fromCurrency=$fromCurrency, fromAmount=$fromAmount, toCurrency=$toCurrency, toAmount=$toAmount)"
    }
}