package practice.myfirstkotlin.restAPI.securityMicroservice
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AdminCredentialsRepository : JpaRepository<AdminCredentials, String>{
    fun findByUsernameAndPassword(username: String, password: String): Optional<AdminCredentials>
}