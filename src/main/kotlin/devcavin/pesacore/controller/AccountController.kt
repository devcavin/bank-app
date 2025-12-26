package devcavin.pesacore.controller

import devcavin.pesacore.dto.request.CreateAccountRequest
import devcavin.pesacore.dto.response.AccountResponse
import devcavin.pesacore.service.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(private val accountService: AccountService) {
    @PostMapping
    fun createAccount(@RequestBody request: CreateAccountRequest): ResponseEntity<AccountResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request))
    }

    @GetMapping("/all")
    fun allAccounts(): ResponseEntity<List<AccountResponse>> {
        return ResponseEntity(
            accountService.allAccounts(),
            HttpStatus.OK
        )
    }

    @GetMapping("/{id}")
    fun accountById(@Valid @PathVariable id: UUID): ResponseEntity<AccountResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(accountService.accountById(id))
    }
}