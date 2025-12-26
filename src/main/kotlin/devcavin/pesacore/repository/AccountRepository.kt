package devcavin.pesacore.repository

import devcavin.pesacore.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface AccountRepository: JpaRepository<Account, UUID> {
    override fun findById(id: UUID): Optional<Account>
}