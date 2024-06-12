package practice.myfirstkotlin.restAPI.securityMicroservice

import org.springframework.stereotype.Service

@Service
class SecurityService(private val adminCredentialsRepository: AdminCredentialsRepository) {

    fun validateCredentials(username: String, password: String) : Boolean {
        return adminCredentialsRepository.findByUsernameAndPassword(username, password).isPresent
    }
}