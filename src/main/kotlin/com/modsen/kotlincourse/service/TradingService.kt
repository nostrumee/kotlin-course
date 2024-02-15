package com.modsen.kotlincourse.service

import com.modsen.kotlincourse.entity.Exchange
import com.modsen.kotlincourse.entity.Transaction
import com.modsen.kotlincourse.entity.User
import com.modsen.kotlincourse.entity.Wallet
import com.modsen.kotlincourse.entity.enums.Currency
import java.math.BigDecimal

interface TradingService {

    fun addExchanges(vararg exchange: Exchange)

    fun getAllExchanges(): Set<Exchange>

    fun swap(
        initiator: User,
        wallet: Wallet,
        passphrase: String,
        fromCurrency: Currency,
        fromAmount: BigDecimal,
        toCurrency: Currency,
        exchange: Exchange
    )

    fun trade(
        initiator: User,
        receiver: User,
        fromWallet: Wallet,
        fromCurrency: Currency,
        fromAmount: BigDecimal,
        toCurrency: Currency,
        toWallet: Wallet,
        exchange: Exchange
    )
}