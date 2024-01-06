package io.ktri.expense.tracker.user.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktri.expense.tracker.common.application.error.BusinessException
import io.ktri.expense.tracker.user.application.ports.`in`.TenantMapper
import io.ktri.expense.tracker.user.application.ports.`in`.TenantUseCase
import io.ktri.expense.tracker.user.application.ports.`in`.dto.GenerateUserCommand
import io.ktri.expense.tracker.user.application.ports.`in`.dto.TenantDto
import io.ktri.expense.tracker.user.application.ports.`in`.dto.UserInfo
import io.ktri.expense.tracker.user.application.ports.out.KeycloakClientPort
import io.ktri.expense.tracker.user.application.ports.out.TenantManagementPort
import io.ktri.expense.tracker.user.application.ports.out.TenantRetrievalPort
import io.ktri.expense.tracker.user.domain.model.Email
import io.ktri.expense.tracker.user.domain.model.Tenant
import io.ktri.expense.tracker.user.domain.model.TenantId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TenantService(
    private val keycloakClientPort: KeycloakClientPort,
    private val tenantManagementPort: TenantManagementPort,
    private val tenantRetrievalPort: TenantRetrievalPort,
    private val tenantMapper: TenantMapper,
    private val userService: UserService,
) : TenantUseCase {
    private val logger = KotlinLogging.logger {}

    @Transactional(transactionManager = "adminTransactionManager")
    override fun generateTenant(tokenUserInfo: UserInfo): TenantDto? {
        val userRepresentation =
            keycloakClientPort.getUserByEmail(tokenUserInfo.email)
                ?: throw UserNotFoundException(
                    "User with email ${tokenUserInfo.email} not found",
                    IllegalStateException()
                )

        userRepresentation.firstAttribute("tenantId")?.let {
            throw TenantCreationException(
                "User with email ${tokenUserInfo.email} already has a tenant with id ${userRepresentation.firstAttribute("tenantId")}}",
                IllegalStateException()
            )
        }
        val tenant: Tenant = Tenant(TenantId.generateId()).create(Email(userRepresentation.email))

        return try {
            val toDto = tenantMapper.toDto(tenantManagementPort.createTenant(tenant))
            userService.generateUser(GenerateUserCommand(tenant.id, userRepresentation.email))
            toDto
        } catch (e: Exception) {
            throw TenantCreationException("Failed to create tenant with id ${tenant.id}", e)
        }
        //        val newTenant =
        //        )
        //        return newTenant

        //        val tenant = Tenant(tenantId)
        //        if (userRepresentation.firstAttribute("tenantId") != null) {}
        //
        //        val newTenant = Tenant(TenantId.generateId())
        //        keycloakClientPort.updateUser(
        //            userRepresentation.singleAttribute("tenantId", newTenant.stringId())
        //        )
        //        return persistTenantOrRollback(newTenant, userRepresentation)?.let { tenantDto ->
        //            persistUserOrRollback(userRepresentation, newTenant)?.let { tenantDto }
        //        }
    }

    //    private fun persistTenantOrRollback(
    //        tenant: Tenant,
    //        userRepresentation: UserRepresentation
    //    ): TenantDto? {
    //        return runCatching {
    //                tenantMapper.toDto(
    //                    tenantManagementPort.createTenant(tenantMapper.toTenant(tenant.id))
    //                )
    //            }
    //            .onFailure { throwable ->
    //                logger.error(throwable) {
    //                    "Failed to create tenant with id ${tenant.id}. Rolling back keycloak
    // update."
    //                }
    //                keycloakClientPort.rollbackUpdateUser(userRepresentation)
    //            }
    //            .getOrElse {
    //                throw TenantCreationException("Failed to create tenant with id ${tenant.id}")
    //            }
    //    }
    //
    //    private fun persistUserOrRollback(
    //        userRepresentation: UserRepresentation,
    //        newTenant: Tenant
    //    ): User? {
    //        return runCatching {
    //                userService.generateUser(
    //                    GenerateUserCommand(newTenant.id, userRepresentation.email)
    //                )
    //            }
    //            .onFailure { throwable ->
    //                logger.error(throwable) {
    //                    "Failed to create user for tenant ${newTenant.id}. Rolling back keycloak
    // update."
    //                }
    //                keycloakClientPort.rollbackUpdateUser(userRepresentation)
    //            }
    //            .getOrElse {
    //                throw UserCreationException(
    //                    "Failed to create user with email ${userRepresentation.email} for tenant
    // ${newTenant.id}",
    //                    it
    //                )
    //            }
    //    }
}

class TenantCreationException(message: String, e: Exception? = null) :
    BusinessException(message, e)

class UserCreationException(message: String, e: Exception? = null) : BusinessException(message, e)

class UserNotFoundException(message: String, e: Exception? = null) : BusinessException(message, e)
