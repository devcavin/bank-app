package devcavin.pesacore.utility

import kotlin.random.Random as random

object AccountUtility {
    private val charPool: List<Char> = ('0'..'9').toList()

    fun generateAccountNumber(): String {
        return (1..10)
            .map { random.nextInt(0, charPool.size) }
            .map { charPool[it]}
            .joinToString("")
    }

    fun generateOwnerId(): String {
        return (1..8)
            .map { random.nextInt(0, 10) }
            .joinToString("")
    }
}