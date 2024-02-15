package com.modsen.kotlincourse.entity

import com.modsen.kotlincourse.entity.enums.Currency
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class Exchange(
    var name: String,
) {
    val exchangeRates = mutableMapOf<Pair<Currency, Currency>, BigDecimal>()
    val transactionHistory = mutableListOf<Transaction>()

    val reversedRates: Map<Pair<Currency, Currency>, BigDecimal>
        get() {
            val reversedRates = mutableMapOf<Pair<Currency, Currency>, BigDecimal>()
            exchangeRates.forEach { (pair, rate) ->
                val (fromCurrency, toCurrency) = pair
                val reversedPair = Pair(toCurrency, fromCurrency)
                val reversedRate = BigDecimal.ONE.divide(rate, MathContext.DECIMAL128).setScale(5, RoundingMode.HALF_UP)
                reversedRates[reversedPair] = reversedRate
            }
            return reversedRates
        }
}