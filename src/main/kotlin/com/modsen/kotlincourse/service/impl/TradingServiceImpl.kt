package com.modsen.kotlincourse.service.impl

import com.modsen.kotlincourse.entity.*
import com.modsen.kotlincourse.entity.enums.Currency
import com.modsen.kotlincourse.entity.enums.UserStatus
import com.modsen.kotlincourse.exception.*
import com.modsen.kotlincourse.service.TradingService
import com.modsen.kotlincourse.util.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class TradingServiceImpl : TradingService {

    private val exchanges = mutableSetOf<Exchange>()

    override fun addExchanges(vararg exchange: Exchange) {
       exchanges.addAll(exchange)
    }

    override fun getAllExchanges(): Set<Exchange> {
        return exchanges
    }

    override fun swap(
        initiator: User,
        wallet: Wallet,
        passphrase: String,
        fromCurrency: Currency,
        fromAmount: BigDecimal,
        toCurrency: Currency,
        exchange: Exchange
    ) {
        checkPassphrase(wallet, passphrase)
        checkWalletSufficientFunds(wallet, fromCurrency, fromAmount)
        checkIfCurrencyPairSupported(exchange, fromCurrency, toCurrency)
        checkIfTransactionFails()

        val exchangeRate = exchange.exchangeRates[Pair(fromCurrency, toCurrency)]
        val toAmount = exchangeRate!!.multiply(fromAmount)
        processTransaction(fromCurrency, wallet, fromAmount, toCurrency, wallet, toAmount)

        val transaction = SwapTransaction(
            initiator,
            fromCurrency,
            fromAmount,
            toCurrency,
            toAmount
        )
        exchange.transactionHistory.add(transaction)
    }

    override fun trade(
        initiator: User,
        receiver: User,
        fromWallet: Wallet,
        fromCurrency: Currency,
        fromAmount: BigDecimal,
        toCurrency: Currency,
        toWallet: Wallet,
        exchange: Exchange
    ) {
        checkUserStatus(initiator)
        checkUserStatus(receiver)
        checkWalletSufficientFunds(fromWallet, fromCurrency, fromAmount)

        val exchangeRate = exchange.exchangeRates[Pair(fromCurrency, toCurrency)]
        val toAmount = exchangeRate!!.multiply(fromAmount)
        processTransaction(fromCurrency, fromWallet, fromAmount, toCurrency, toWallet, toAmount)

        val transaction = TradeTransaction(
            initiator,
            fromCurrency,
            fromAmount,
            receiver,
            toCurrency,
            toAmount
        )
        exchange.transactionHistory.add(transaction)
    }

    private fun checkPassphrase(wallet: Wallet, passphrase: String) {
        if (wallet.passphrase != passphrase)
            throw PassphraseNotMatchException(PASSPHRASE_NOT_MATCH_MESSAGE)
    }

    private fun checkWalletSufficientFunds(
        wallet: Wallet,
        fromCurrency: Currency,
        fromCurrencyAmount: BigDecimal
    ) {

        if (wallet.cryptoCurrencies[fromCurrency] == null)
            throw NotSupportedCurrencyException(
                CURRENCY_NOT_SUPPORTED_MESSAGE.format(fromCurrency.name)
            )

        if (fromCurrencyAmount > wallet.cryptoCurrencies[fromCurrency])
            throw InsufficientFundsException(INSUFFICIENT_FUNDS_MESSAGE)
    }

    private fun checkIfCurrencyPairSupported(exchange: Exchange, fromCurrency: Currency, toCurrency: Currency) {
        if (exchange.exchangeRates[Pair(fromCurrency, toCurrency)] == null)
            throw CurrencyPairNotSupported(
                CURRENCY_PAIR_NOT_SUPPORTED_MESSAGE.format(fromCurrency.name, toCurrency.name)
            )
    }

    private fun checkIfTransactionFails() {
        val random = Random().nextInt(0, 51)
        if (random in 27..50) {
            throw TransactionFailedException(TRANSACTION_FAILED_MESSAGE)
        }
    }

    private fun checkUserStatus(user: User) {
        if (user.status == UserStatus.BLOCKED || user.status == UserStatus.NEW)
            throw UserStatusNotSupportedException(
                USER_STATUS_NOT_SUPPORTED_EXCEPTION.format(user.status.name)
            )
    }

    private fun processTransaction(
        fromCurrency: Currency,
        fromWallet: Wallet,
        fromAmount: BigDecimal,
        toCurrency: Currency,
        toWallet: Wallet,
        toAmount: BigDecimal
    ) {
        fromWallet.cryptoCurrencies[fromCurrency] = fromWallet.cryptoCurrencies[fromCurrency]!!
            .minus(fromAmount)
            .setScale(5, RoundingMode.HALF_UP)

        toWallet.cryptoCurrencies[toCurrency] = toWallet.cryptoCurrencies
            .getOrDefault(toCurrency, BigDecimal.ZERO)
            .plus(toAmount)
            .setScale(5, RoundingMode.HALF_UP)
    }
}