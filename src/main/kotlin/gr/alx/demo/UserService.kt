package gr.alx.demo

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userManagementPort: UserManagementPort,
) {
    @Transactional//(transactionManager = "adminTransactionManager")
    fun generateUser(command: GenerateUserCommand): User? {
        val newUser = User(UUID.randomUUID(), command.email, command.tenantId)
        return userManagementPort.handlePotentialNewUser(newUser)
    }
}

data class GenerateUserCommand(
    val tenantId: UUID,
    val email: String,
)
