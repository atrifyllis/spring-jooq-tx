package gr.alx.demo

import java.util.*

interface UserManagementPort {
    open fun handlePotentialNewUser(command: User): User?

    fun retrieveUserByEmail(email: String): User?

    fun deleteAll()
}


data class User(val id: UUID, val email: String, val tenantId: UUID)
