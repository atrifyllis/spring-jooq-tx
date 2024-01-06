package io.ktri.expense.tracker.user.application.service

import io.ktri.expense.tracker.user.application.ports.`in`.UserInfoMapper
import io.ktri.expense.tracker.user.application.ports.`in`.UserUseCase
import io.ktri.expense.tracker.user.application.ports.`in`.dto.GenerateUserCommand
import io.ktri.expense.tracker.user.application.ports.`in`.dto.UserInfo
import io.ktri.expense.tracker.user.application.ports.out.UserManagementPort
import io.ktri.expense.tracker.user.application.ports.out.UserRetrievalPort
import io.ktri.expense.tracker.user.domain.model.Email
import io.ktri.expense.tracker.user.domain.model.TenantCreated
import io.ktri.expense.tracker.user.domain.model.User
import io.ktri.expense.tracker.user.domain.model.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userManagementPort: UserManagementPort,
    private val userRetrievalPort: UserRetrievalPort,
    private val userInfoMapper: UserInfoMapper,
) : UserUseCase {
    @Transactional(transactionManager = "adminTransactionManager")
    override fun generateUser(command: GenerateUserCommand): User? {
        val newUser = User(UserId.generateId(), Email(command.email), command.tenantId)
        return userManagementPort.handlePotentialNewUser(newUser)
    }

    override fun findAllUsers(): List<UserInfo> {
        return userInfoMapper.toDto(userRetrievalPort.findAllUsers())
    }

    //    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    //    @EventListener
    //    @Transactional
    fun handleTenantCreatedEvent(event: TenantCreated) {
        try {
            generateUser(GenerateUserCommand(event.tenantId, event.creatorEmail.address))
        } catch (e: Exception) {
            throw UserCreationException(
                "Failed to create user with email ${event.creatorEmail.address} for tenant ${event.tenantId}",
                e
            )
        }
    }
}
