package devcavin.pesacore.repository

import devcavin.pesacore.entity.Transaction
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.Optional
import java.util.UUID

interface TransactionRepository: JpaRepository<Transaction, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    override fun findById(id: UUID): Optional<Transaction>
}