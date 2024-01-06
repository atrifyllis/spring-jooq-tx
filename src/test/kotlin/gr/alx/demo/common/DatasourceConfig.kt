package gr.alx.demo.common

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

//@Configuration
//@EnableJpaRepositories(
//    entityManagerFactoryRef = "tenantEntityManagerFactory",
//    transactionManagerRef = "tenantTransactionManager",
//    basePackages = ["gr.alx.demo"]
//)
class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("multitenancy.tenant.datasource")
    fun tenantDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    @ConfigurationProperties("multitenancy.tenant.datasource.hikari")
    fun tenantDataSource(): DataSource {
        val dataSource =
            tenantDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource::class.java)
                .build()
        //                    .apply { poolName = "tenantDataSource" }
        return dataSource
    }
//
//    @Bean("transactionManager")
//    @Primary
//    fun transactionManager(
//        transactionManagerCustomizers: ObjectProvider<TransactionManagerCustomizers>,
//        // @Qualifier("entityManagerFactory")
//        emf: EntityManagerFactory
//    ): PlatformTransactionManager {
//        val transactionManager = JpaTransactionManager(emf)
//        transactionManagerCustomizers.ifAvailable { customizers: TransactionManagerCustomizers ->
//            customizers.customize(transactionManager)
//        }
//        return transactionManager
//    }
//
//    @Bean(name = ["tenantEntityManagerFactory"])
//    @Primary
//    fun songEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
//        val entityManager = LocalContainerEntityManagerFactoryBean()
//        entityManager.dataSource = tenantDataSource()
//        entityManager.setPackagesToScan("io.ktri.expense.tracker")
//        entityManager.persistenceUnitName = "tenantDatasource"
//
//        val vendorAdapter = HibernateJpaVendorAdapter()
//        //        vendorAdapter.setGenerateDdl(true)
//        entityManager.jpaVendorAdapter = vendorAdapter
//        return entityManager
//    }
}
