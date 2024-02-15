package com.modsen.kotlincourse

import com.modsen.kotlincourse.entity.Exchange
import com.modsen.kotlincourse.entity.User
import com.modsen.kotlincourse.entity.Wallet
import com.modsen.kotlincourse.entity.enums.Currency
import com.modsen.kotlincourse.entity.enums.UserStatus
import com.modsen.kotlincourse.service.impl.PersonalAccountServiceImpl
import com.modsen.kotlincourse.service.impl.TradingServiceImpl
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

fun main() {
    // init
    val binance = Exchange("Binance")
    binance.exchangeRates.putAll(
        mapOf(
            Pair(Currency.BTC, Currency.ETH) to BigDecimal("18.88"),
            Pair(Currency.ETH, Currency.XMR) to BigDecimal("20.53"),
            Pair(Currency.XMR, Currency.LTC) to BigDecimal("1.72"),
            Pair(Currency.LTC, Currency.BTC) to BigDecimal("0.0015"),
            Pair(Currency.BTC, Currency.BNB) to BigDecimal("145.52"),
            Pair(Currency.ETH, Currency.BTC) to BigDecimal("0.053"),
            Pair(Currency.XMR, Currency.BNB) to BigDecimal("0.38"),
            Pair(Currency.LTC, Currency.ETH) to BigDecimal("0.025"),
            Pair(Currency.BNB, Currency.LTC) to BigDecimal("4.56"),
            Pair(Currency.BNB, Currency.BTC) to BigDecimal("0.0069")
        )
    )

    val user1 = User(email = "email1", fullName = "name1", status = UserStatus.APPROVED)
    val user2 = User(email = "email2", fullName = "name2", status = UserStatus.APPROVED)
    val user3 = User(email = "email3", fullName = "name3", status = UserStatus.NEW)
    val user4 = User(email = "email4", fullName = "name4", status = UserStatus.BLOCKED)

    val wallet1 = Wallet(name = "wallet1", passphrase = "passphrase1", user = user1)
    val wallet2 = Wallet(name = "wallet2", passphrase = "passphrase2", user = user1)
    val wallet3 = Wallet(name = "wallet3", passphrase = "passphrase3", user = user2)
    val wallet4 = Wallet(name = "wallet4", passphrase = "passphrase4", user = user3)
    val wallet5 = Wallet(name = "wallet5", passphrase = "passphrase5", user = user4)

    loadWallet(wallet1)
    loadWallet(wallet2)
    loadWallet(wallet3)
    loadWallet(wallet4)
    loadWallet(wallet5)

    // addNewWallet
    val personalAccountService = PersonalAccountServiceImpl()
    personalAccountService.addNewWallet(user1, wallet1)
    personalAccountService.addNewWallet(user1, wallet2)
    personalAccountService.addNewWallet(user2, wallet3)
    personalAccountService.addNewWallet(user3, wallet4)
    personalAccountService.addNewWallet(user4, wallet5)

    if (user1.wallets.count() != 2)
        throw Exception()

    // printWalletsBalances
    personalAccountService.printWalletsBalances(wallet1, wallet2)

    // swap & printTransactionHistoryByExchangeAndTimePeriod
    val tradingService = TradingServiceImpl()
    tradingService.addExchanges(binance)

    val startDate = LocalDateTime.of(2023, 1, 20, 0, 0).toInstant(ZoneOffset.UTC)
    val endDate = LocalDateTime.of(2025, 1, 20, 0, 0).toInstant(ZoneOffset.UTC)

    tradingService.swap(
        initiator = user1,
        wallet = wallet1,
        passphrase = "passphrase1",
        fromCurrency = Currency.BTC,
        fromAmount = BigDecimal("2"),
        toCurrency = Currency.ETH,
        exchange = binance
    )
    personalAccountService.printTransactionHistoryByExchangeAndTimePeriod(user1, binance, startDate, endDate)
    personalAccountService.printWalletsBalances(wallet1)

    // trade
    tradingService.trade(
        initiator = user1,
        receiver = user2,
        fromWallet = wallet1,
        fromCurrency =  Currency.BTC,
        fromAmount = BigDecimal("20"),
        toCurrency = Currency.ETH,
        toWallet = wallet3,
        exchange = binance
    )
    personalAccountService.printWalletsBalances(wallet1, wallet3)
    personalAccountService.printTransactionHistoryByExchangeAndTimePeriod(user1, binance, startDate, endDate)

}

fun loadWallet(wallet: Wallet) {
    wallet.cryptoCurrencies.putAll(
        mutableMapOf(
            Currency.BTC to BigDecimal("100"),
            Currency.ETH to BigDecimal("100"),
            Currency.XMR to BigDecimal("100"),
            Currency.LTC to BigDecimal("100"),
            Currency.BNB to BigDecimal("100")
        )
    )
}