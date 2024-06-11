package practice.myfirstkotlin.restAPI.services

import org.springframework.stereotype.Service
import practice.myfirstkotlin.restAPI.repositories.AdminCredentialsRepository

@Service
class SecurityService(private val adminCredentialsRepository: AdminCredentialsRepository) {

    fun validateCredentials(username: String, password: String) : Boolean {
        return adminCredentialsRepository.findByUsernameAndPassword(username, password).isPresent
    }
}