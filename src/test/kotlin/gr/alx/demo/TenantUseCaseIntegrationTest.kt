package gr.alx.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class TenantUseCaseIntegrationTest : BasePostgresTest() {

    @Autowired
    private lateinit var cut: TenantService

    @Autowired
    private lateinit var tenantRetrievalPort: TenantRetrievalPort

    @Test
    fun `should store new tenant and user`() {

        val info = UserInfo("test_user", TEST_EMAIL_NEW, emptyList())

        val tenant = cut.generateTenant(info)

        assertThat(tenant).isNotNull()
        assertThat(tenant?.id).isNotNull()


        val user: User? = userManagementPort.retrieveUserByEmail(info.email)

        assertThat(user).isNotNull()
        assertThat(user?.email).isEqualTo(Companion.TEST_EMAIL_NEW)
    }


    @Test
    fun `should not save tenant when user insertion fails`() {

        assertThrows<TenantCreationException> {
            cut.generateTenant(UserInfo("test_user", TEST_EMAIL_TOO_LONG, emptyList()))
        }

        assertThat(tenantRetrievalPort.findAllTenantsJooq()).isEmpty()
    }

    companion object {
        private const val TEST_EMAIL_TOO_LONG = "testtesttest@email.com"
        private const val TEST_EMAIL_NEW = "test_new@email.com"
    }
}
