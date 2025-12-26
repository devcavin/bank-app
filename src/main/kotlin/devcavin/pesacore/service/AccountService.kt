package devcavin.pesacore.service

import devcavin.pesacore.dto.request.CreateAccountRequest
import devcavin.pesacore.dto.request.toAccountEntity
import devcavin.pesacore.dto.response.AccountResponse
import devcavin.pesacore.dto.response.toAccountResponse
import devcavin.pesacore.exception.ResourceNotFoundException
import devcavin.pesacore.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountService(private val accountRepository: AccountRepository) {
    // POST account
    fun createAccount(request: CreateAccountRequest): AccountResponse {
        return accountRepository.save(request.toAccountEntity()).toAccountResponse()
    }

    // all accounts
    fun allAccounts(): List<AccountResponse> {
        return accountRepository.findAll().map { it.toAccountResponse() }
    }

    // find account by ID
    fun accountById(id: UUID): AccountResponse {
        return accountRepository.findById(id).map { it.toAccountResponse() }
            .orElseThrow { ResourceNotFoundException("Account not found") }
    }
}