package practice.myfirstkotlin.restAPI.entities
import org.springframework.stereotype.Component
import jakarta.persistence.EntityManager
import org.springframework.transaction.annotation.Transactional

@Component
class ScenarioInitializer(private val entityManager: EntityManager) {

    @Transactional
    fun initializeTables() {
        entityManager.persist(Tables(capacity = 2))
        entityManager.persist(Tables(capacity =2))
        entityManager.persist(Tables(capacity =4))
        entityManager.persist(Tables(capacity =6))
    }
    @Transactional
    fun initializeAdmin() {
        entityManager.persist(AdminCredentials(username = "admin", password = "password"))
    }
}