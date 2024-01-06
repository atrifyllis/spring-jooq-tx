package io.ktri.expense.tracker.user.application.ports.out

import io.ktri.expense.tracker.user.domain.model.Tenant
import java.util.*

interface TenantRetrievalPort {
    fun checkTenantExists(tenantId: UUID): Boolean

    fun findAllTenants(): List<Tenant>

    fun findAllTenantsJooq(): List<Tenant>
}
