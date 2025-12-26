package devcavin.pesacore.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SystemController {
    @GetMapping("/")
    fun getSystemStatus(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("PesaCore Ledger System is operational")
    }
}