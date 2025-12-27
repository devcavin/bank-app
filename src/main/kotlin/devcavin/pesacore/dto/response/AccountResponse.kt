package devcavin.pesacore.dto.response

import devcavin.pesacore.entity.Account
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class AccountResponse(
    val id: UUID?,
    val accountNumber: String,
    val ownerId: String,
    val balance: BigDecimal?,
    val currency: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun Account.toAccountResponse(): AccountResponse {
    return AccountResponse(
        id = this.id,
        accountNumber = this.accountNumber,
        ownerId = this.ownerId,
        balance = this.balance,
        currency = this.currency,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now()
    )
}
