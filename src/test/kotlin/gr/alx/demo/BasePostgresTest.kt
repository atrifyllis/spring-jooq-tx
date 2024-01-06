package gr.alx.demo


import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BasePostgresTest {

    @SpyBean
    lateinit var tenantManagementPort: TenantManagementPort

    @SpyBean
    lateinit var userManagementPort: UserManagementPort

    companion object {
        @Container
        val postgresContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:alpine3.19")
                .withTmpFs(mapOf("/var/lib/postgresql/data" to "rw"))
                .withUsername("core")
                .withPassword("core")
                .withCommand("postgres", "-c", "fsync=off", "-c", "log_statement=all")

        @JvmStatic
        @DynamicPropertySource
        fun initialize(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
//            registry.add("multitenancy.admin.datasource.url", postgresContainer::getJdbcUrl)
//            registry.add("multitenancy.tenant.datasource.url", postgresContainer::getJdbcUrl)
        }
    }

    @BeforeEach
    fun setup() {
        userManagementPort.deleteAll()
        tenantManagementPort.deleteAll()
    }
}
