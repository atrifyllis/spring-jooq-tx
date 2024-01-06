package gr.alx.demo

import gr.alx.demo.tables.references.TENANT
import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class TenantRetrieverAdapter(
    private val dslContext: DSLContext,
) : TenantRetrievalPort {


    //    @Transactional(transactionManager = "masterTransactionManager")
    override fun findAllTenantsJooq(): List<Tenant> {
        return dslContext.select().from(TENANT).fetchInto(Tenant::class.java)
    }
}
