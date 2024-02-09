package com.modsen.kotlincourse.service.impl

import com.modsen.kotlincourse.entity.Exchange
import com.modsen.kotlincourse.entity.User
import com.modsen.kotlincourse.entity.Wallet
import com.modsen.kotlincourse.service.PersonalAccountService
import java.time.Instant

class PersonalAccountServiceImpl : PersonalAccountService {

    override fun printWalletsBalances(vararg wallets: Wallet) = wallets.forEach { wallet ->
        println("Balances for wallet ${wallet.id}:")
        wallet.cryptoCurrencies.forEach { (currency, balance) ->
            println("${currency.name}: $balance")
        }
    }

    override fun printTransactionHistoryByExchangeAndTimePeriod(
        user: User,
        exchange: Exchange,
        startDate: Instant,
        endDate: Instant
    ) {
        exchange.transactionHistory
            .filter { it.date in startDate..endDate && user.id == it.initiator.id }
            .forEach { println(it) }
    }

    override fun addNewWallet(user: User, wallet: Wallet) : Boolean = user.wallets.add(wallet)
}