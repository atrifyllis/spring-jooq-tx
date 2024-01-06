package gr.alx.demo

interface TenantRetrievalPort {

    fun findAllTenantsJooq(): List<Tenant>
}
