package practice.myfirstkotlin.restAPI.restControllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import practice.myfirstkotlin.restAPI.entities.AdminCredentials
import practice.myfirstkotlin.restAPI.entities.ScenarioInitializer
import practice.myfirstkotlin.restAPI.security.JWTUtil
import practice.myfirstkotlin.restAPI.services.SecurityService

@RestController
class SecurityController(
    scenarioInitializer: ScenarioInitializer,
    private val securityService: SecurityService,
    service: SecurityService
) {
    init{
        scenarioInitializer.initializeAdmin();
    }

    @PostMapping("/admin")
    fun login(@RequestBody adminCredentials: AdminCredentials): ResponseEntity<Any> {
        return if (securityService.validateCredentials(username = adminCredentials.username, password = adminCredentials.password)) {
            val token = JWTUtil.generateToken(adminCredentials.username)
            val response = mapOf("token" to token)
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.status(401).body("Invalid credentials")
        }
    }
}

