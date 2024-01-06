package io.ktri.expense.tracker.common

import io.ktri.expense.tracker.user.application.ports.out.KeycloakClientPort
import io.ktri.expense.tracker.user.application.ports.out.TenantManagementPort
import io.ktri.expense.tracker.user.application.ports.out.UserManagementPort
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BasePostgresTest {
    @MockBean lateinit var keycloakClientPort: KeycloakClientPort

    @SpyBean lateinit var tenantManagementPort: TenantManagementPort

    @SpyBean lateinit var userManagementPort: UserManagementPort

    companion object {
        @Container
        val postgresContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:alpine3.19")
                .withTmpFs(mapOf("/var/lib/postgresql/data" to "rw"))
                .withUsername("core")
                .withPassword("core")
                .withInitScript("init.sql") // TODO find better way to create user on startup
                .withCommand("postgres", "-c", "fsync=off", "-c", "log_statement=all")

        @JvmStatic
        @DynamicPropertySource
        fun initialize(registry: DynamicPropertyRegistry) {
            registry.add("multitenancy.master.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("multitenancy.tenant.datasource.url", postgresContainer::getJdbcUrl)
        }
    }

    @BeforeEach
    fun setup() {
        userManagementPort.deleteAll()
        tenantManagementPort.deleteAll()
    }
}
