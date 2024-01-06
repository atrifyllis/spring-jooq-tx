package gr.alx.demo

import java.util.*

interface TenantManagementPort {
    fun createTenant(tenant: Tenant): Tenant?

    fun deleteAll()
}


class Tenant(val id: UUID) {

    fun stringId(): String {
        return id.toString()
    }


}
