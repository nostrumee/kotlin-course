package com.modsen.kotlincourse.entity

import com.modsen.kotlincourse.entity.enums.Currency
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class TradeTransaction(
    id: UUID,
    date: Instant,
    initiator: User,
    fromCurrency: Currency,
    fromAmount: BigDecimal,
    val receiver: User,
    val toCurrency: Currency,
    val toAmount: BigDecimal,
) : Transaction(id, date, initiator, fromCurrency, fromAmount) {
    constructor(
        initiator: User,
        fromCurrency: Currency,
        fromAmount: BigDecimal,
        receiver: User,
        toCurrency: Currency,
        toAmount: BigDecimal
    ) : this(
        UUID.randomUUID(),
        Instant.now(),
        initiator,
        fromCurrency,
        fromAmount,
        receiver,
        toCurrency,
        toAmount
    )

    override fun toString(): String {
        return "TradeTransaction(date=$date, initiator=$initiator, fromCurrency=$fromCurrency, fromAmount=$fromAmount, receiver=$receiver, toCurrency=$toCurrency, toAmount=$toAmount)"
    }
}