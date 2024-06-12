package practice.myfirstkotlin.restAPI.securityMicroservice
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "admin_credentials")
class AdminCredentials(
    @Id
    val username: String,

    val password: String
)