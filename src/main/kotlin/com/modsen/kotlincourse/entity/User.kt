package com.modsen.kotlincourse.entity

import com.modsen.kotlincourse.entity.enums.UserStatus
import java.util.*

class User(
        val id: UUID = UUID.randomUUID(),
        var email: String,
        var fullName: String,
        val wallets: MutableSet<Wallet> = mutableSetOf(),
        var status: UserStatus = UserStatus.NEW,
) {
        constructor(email: String, fullName: String) : this(
                UUID.randomUUID(),
                email,
                fullName,
                mutableSetOf<Wallet>(),
                UserStatus.NEW
        )

        override fun toString(): String {
                return "User(email='$email', fullName='$fullName', status=$status)"
        }
}