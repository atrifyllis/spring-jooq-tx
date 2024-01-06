package gr.alx.demo

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TenantService(
    private val tenantManagementPort: TenantManagementPort,
    private val userService: UserService,
) {

    @Transactional//(transactionManager = "adminTransactionManager")
    fun generateTenant(tokenUserInfo: UserInfo): Tenant? {

        val tenant = Tenant(UUID.randomUUID())

        return try {
            val toDto = tenantManagementPort.createTenant(tenant)
            userService.generateUser(GenerateUserCommand(tenant.id, tokenUserInfo.email))
            toDto
        } catch (e: Exception) {
            throw TenantCreationException("Failed to create tenant with id ${tenant.id}", e)
        }

    }


}

open class BusinessException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, e: Exception? = null) : super(message, e)
}

class TenantCreationException(message: String, e: Exception? = null) :
    BusinessException(message, e)

class UserCreationException(message: String, e: Exception? = null) : BusinessException(message, e)

class UserNotFoundException(message: String, e: Exception? = null) : BusinessException(message, e)

data class UserInfo(
    val username: String?,
    val email: String,
    val authorities: List<String>?
)
