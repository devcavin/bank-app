package devcavin.pesacore.dto.response

import devcavin.pesacore.entity.Transaction
import devcavin.pesacore.entity.TransactionStatus
import devcavin.pesacore.entity.TransactionType
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class TransactionResponse(
    val id: UUID?,
    val account: AccountResponse,
    val type: TransactionType,
    val status: TransactionStatus,
    val amount: BigDecimal?,
    val reason: String?,
    val createdAt: Instant,
)

fun Transaction.toTransactionResponse(): TransactionResponse {
    return TransactionResponse(
        id = this.id,
        account = this.account.toAccountResponse(),
        type = this.type,
        status = this.status,
        amount = this.amount,
        reason = this.reason,
        createdAt = this.createdAt ?: Instant.now()
    )
}
