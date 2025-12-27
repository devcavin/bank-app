package devcavin.pesacore.dto.request

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class TransactionRequest(
    @field:NotNull(message = "Amount is required")
    @field:DecimalMin(value = "0.01", message = "Amount cannot be negative or zero", inclusive = true)
    var amount: BigDecimal? = null
)