package io.ktri.expense.tracker.user.adapters.secondary.persistence

import io.ktri.expense.tracker.user.adapters.secondary.persistence.tables.references.TENANT
import io.ktri.expense.tracker.user.application.ports.out.TenantRetrievalPort
import io.ktri.expense.tracker.user.domain.model.Tenant
import java.util.*
import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class TenantRetrieverAdapter(
    private val tenantRepository: TenantRepository,
    private val tenantEntityMapper: TenantEntityMapper,
    private val dslContext: DSLContext,
) : TenantRetrievalPort {
    override fun checkTenantExists(tenantId: UUID): Boolean {
        return tenantRepository.existsById(tenantId)
    }

    override fun findAllTenants(): List<Tenant> {
        return tenantRepository.findAll().map { tenantEntityMapper.toModel(it) }
    }

    //    @Transactional(transactionManager = "masterTransactionManager")
    override fun findAllTenantsJooq(): List<Tenant> {
        return dslContext.select().from(TENANT).fetchInto(Tenant::class.java)
    }
}
