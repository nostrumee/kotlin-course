package com.modsen.kotlincourse.service

import com.modsen.kotlincourse.entity.Exchange
import com.modsen.kotlincourse.entity.User
import com.modsen.kotlincourse.entity.Wallet
import java.time.Instant

interface PersonalAccountService {

    fun printWalletsBalances(vararg wallets: Wallet)

    fun printTransactionHistoryByExchangeAndTimePeriod(
        user: User,
        exchange: Exchange,
        startDate: Instant,
        endDate: Instant
    )

    fun addNewWallet(user: User, wallet: Wallet) : Boolean
}