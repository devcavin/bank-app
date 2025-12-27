package devcavin.pesacore.dto.request

import devcavin.pesacore.entity.Account
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CreateAccountRequest(
    @field:NotNull(message = "Balance is required")
    @field:DecimalMin(value = "0.0", message = "Balance cannot be negative", inclusive = true)
    var balance: BigDecimal? = null
)

fun CreateAccountRequest.toAccountEntity(): Account {
    return Account(balance = this.balance)
}