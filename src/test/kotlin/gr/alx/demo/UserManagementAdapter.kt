package gr.alx.demo

import gr.alx.demo.tables.records.UserInfoRecord
import gr.alx.demo.tables.references.USER_INFO
import org.jooq.DSLContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserManagementAdapter(val dslContext: DSLContext) : UserManagementPort {
    @Transactional(transactionManager = "adminTransactionManager")
    override fun handlePotentialNewUser(command: User): User? {
        return retrieveUserByEmail(command.email) ?: createUser(command)
    }

    override fun retrieveUserByEmail(email: String): User? {
        return dslContext
            .select()
            .from(USER_INFO)
            .where(USER_INFO.EMAIL.eq(email))
            .fetchInto(User::class.java)
            .firstOrNull()
    }

    override fun deleteAll() {
        dslContext.delete(USER_INFO).execute()
    }


    private fun createUser(user: User): User? {
        val userInfoRecord: UserInfoRecord =
            dslContext.newRecord(USER_INFO).apply {
                tenantId = user.tenantId
                id = user.id
                this.email = user.email
            }
        userInfoRecord.store()

        return userInfoRecord.into(User::class.java)
    }
}
