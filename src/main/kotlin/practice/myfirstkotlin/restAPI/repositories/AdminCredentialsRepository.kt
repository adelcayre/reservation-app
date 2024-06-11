package practice.myfirstkotlin.restAPI.repositories
import org.springframework.data.jpa.repository.JpaRepository
import practice.myfirstkotlin.restAPI.entities.AdminCredentials
import java.util.Optional

interface AdminCredentialsRepository : JpaRepository<AdminCredentials, String>{
    fun findByUsernameAndPassword(username: String, password: String): Optional<AdminCredentials>
}