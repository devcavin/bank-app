package devcavin.pesacore.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

enum class TransactionType { DEPOSIT, WITHDRAW, TRANSFER }
enum class TransactionStatus { SUCCESS, FAILED }

@Entity
@Table(name = "transactions")
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @Enumerated(EnumType.STRING)
    var type: TransactionType,

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.SUCCESS,

    var amount: BigDecimal? = null,

    var reason: String? = null
) {
    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: Instant? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Transaction) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String = "Transaction(id=$id, accountId=${account.id}, type=$type, status=$status, " +
            "amount=$amount, " +
                "reason=$reason, createdAt=$createdAt)"
}