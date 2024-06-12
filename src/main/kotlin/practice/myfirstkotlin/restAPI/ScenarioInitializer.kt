package practice.myfirstkotlin.restAPI
import org.springframework.stereotype.Component
import jakarta.persistence.EntityManager
import org.springframework.transaction.annotation.Transactional
import practice.myfirstkotlin.restAPI.securityMicroservice.AdminCredentials
import practice.myfirstkotlin.restAPI.tablesMicroservice.Tables

@Component
class ScenarioInitializer(private val entityManager: EntityManager) {

    @Transactional
    fun initializeTables() {
        entityManager.persist(Tables(capacity = 2))
        entityManager.persist(Tables(capacity =  2))
        entityManager.persist(Tables(capacity = 4))
        entityManager.persist(Tables(capacity = 6))
    }
    @Transactional
    fun initializeAdmin() {
        entityManager.persist(AdminCredentials(username = "admin", password = "password"))
    }
}