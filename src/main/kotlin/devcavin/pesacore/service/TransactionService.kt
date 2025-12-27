package devcavin.pesacore.service

import devcavin.pesacore.dto.request.TransactionRequest
import devcavin.pesacore.dto.response.TransactionResponse
import devcavin.pesacore.dto.response.toTransactionResponse
import devcavin.pesacore.entity.Transaction
import devcavin.pesacore.entity.TransactionStatus
import devcavin.pesacore.entity.TransactionType
import devcavin.pesacore.exception.InsufficientFundsException
import devcavin.pesacore.exception.InvalidAmountException
import devcavin.pesacore.exception.ResourceNotFoundException
import devcavin.pesacore.repository.AccountRepository
import devcavin.pesacore.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {

    @Transactional(noRollbackFor = [InvalidAmountException::class])
    fun deposit(accountId: UUID, request: TransactionRequest): TransactionResponse {
        val account = accountRepository.findById(accountId)
            .orElseThrow { ResourceNotFoundException("Account not found") }

        request.amount?.let {
            if (it <= BigDecimal.ZERO) {
                val failedDeposit = Transaction(
                    account = account,
                    type = TransactionType.DEPOSIT,
                    status = TransactionStatus.FAILED,
                    amount = request.amount,
                    reason = "Amount is less than or equal to zero"
                )

                transactionRepository.save(failedDeposit).toTransactionResponse()

                throw InvalidAmountException("Amount must be greater than zero")
            }
        }

        account.balance = account.balance?.add(request.amount)
        accountRepository.save(account)

        val transaction = Transaction(
            account = account,
            type = TransactionType.DEPOSIT,
            status = TransactionStatus.SUCCESS,
            amount = request.amount,
            reason = "Deposit transaction"
        )

        return transactionRepository.save(transaction).toTransactionResponse()
    }

    @Transactional(noRollbackFor = [InsufficientFundsException::class, InvalidAmountException::class])
    fun withdraw(accountId: UUID, request: TransactionRequest): TransactionResponse {
        val account = accountRepository.findById(accountId)
            .orElseThrow { ResourceNotFoundException("Account not found") }

        request.amount?.let {
            if (it <= BigDecimal.ZERO) {
                val failedWithdrawal = Transaction(
                    account = account,
                    type = TransactionType.WITHDRAW,
                    status = TransactionStatus.FAILED,
                    amount = request.amount,
                    reason = "Amount is less than or equal to zero"
                )

                transactionRepository.save(failedWithdrawal).toTransactionResponse()

                throw InvalidAmountException("Amount must be greater than zero")
            }
        }

        account.balance?.let {
            if (it < request.amount) {
                val failedWithdrawal = Transaction(
                    account = account,
                    type = TransactionType.WITHDRAW,
                    status = TransactionStatus.FAILED,
                    amount = request.amount,
                    reason = "Insufficient funds"
                )

                transactionRepository.save(failedWithdrawal).toTransactionResponse()

                throw InsufficientFundsException("Insufficient funds to complete transaction")
            }
        }

        account.balance = account.balance?.subtract(request.amount)

        accountRepository.save(account)

        val transaction = Transaction(
            account = account,
            type = TransactionType.WITHDRAW,
            status = TransactionStatus.SUCCESS,
            amount = request.amount,
            reason = "Withdrawal transaction"
        )

        return transactionRepository.save(transaction).toTransactionResponse()
    }
}