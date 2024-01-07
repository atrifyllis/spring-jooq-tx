package gr.alx.demo

import gr.alx.demo.tables.references.TENANT
import org.jooq.DSLContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TenantManagementAdapter(
    private val dslContext: DSLContext,
) : TenantManagementPort {
    @Transactional(transactionManager = "adminTransactionManager")
    override fun createTenant(tenant: Tenant): Tenant? {

        val tenantRecord =
            dslContext.newRecord(TENANT).apply {
                id = tenant.id
                createdBy = getCurrentUser()
                created = LocalDateTime.now()
                lastModifiedBy = getCurrentUser()
                lastModified = LocalDateTime.now()
            }
        tenantRecord.store()
        return tenantRecord.into(Tenant::class.java)
    }

    private fun getCurrentUser(): String {
        return "unknown"
    }

    override fun deleteAll() {
        dslContext.deleteFrom(TENANT).execute()
    }
}
