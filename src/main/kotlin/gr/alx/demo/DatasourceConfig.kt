package gr.alx.demo

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.jooq.ConnectionProvider
import org.jooq.ExecuteListenerProvider
import org.jooq.TransactionProvider
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.DefaultExecuteListenerProvider
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator
import org.springframework.boot.autoconfigure.jooq.JooqProperties
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

//@Configuration
//@EnableJpaRepositories(
//    entityManagerFactoryRef = "adminEntityManagerFactory",
//    transactionManagerRef = "adminTransactionManager",
//    basePackages = ["gr.alx.demo.admin"],
//)
//@EnableConfigurationProperties(JooqProperties::class)
class AdminDataSourceConfig {
//    @Bean
//    @ConfigurationProperties("multitenancy.admin.datasource")
//    fun adminDataSourceProperties(): DataSourceProperties {
//        return DataSourceProperties()
//    }
//
//    @Bean
//    @FlywayDataSource
//    @ConfigurationProperties("multitenancy.admin.datasource.hikari")
//    fun adminDataSource(): DataSource {
//        val dataSource =
//            adminDataSourceProperties()
//                .initializeDataSourceBuilder()
//                .type(HikariDataSource::class.java)
//                .build()
//        return dataSource
//    }
//
//    @Bean
//    fun configurationCustomiser(): DefaultConfigurationCustomizer {
//        return DefaultConfigurationCustomizer { c -> c.setDataSource(adminDataSource()) }
//    }
//
//    @Bean
//    fun dataSourceConnectionProvider(): DataSourceConnectionProvider {
//        return DataSourceConnectionProvider(TransactionAwareDataSourceProxy(adminDataSource()))
//    }
//
//    @Bean("adminTransactionManager")
//    fun transactionManager(
//        transactionManagerCustomizers: ObjectProvider<TransactionManagerCustomizers>,
//        @Qualifier("adminEntityManagerFactory") emf: EntityManagerFactory
//    ): PlatformTransactionManager {
//        val transactionManager = JpaTransactionManager(emf)
//        transactionManagerCustomizers.ifAvailable { customizers: TransactionManagerCustomizers ->
//            customizers.customize(transactionManager)
//        }
//        return transactionManager
//    }
//
//    @Bean(name = ["adminEntityManagerFactory"])
//    fun adminEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
//        val entityManager = LocalContainerEntityManagerFactoryBean()
//        entityManager.dataSource = adminDataSource()
//        entityManager.setPackagesToScan("gr.alx.demo.admin")
//        entityManager.persistenceUnitName = "adminDatasource"
//
//        val vendorAdapter = HibernateJpaVendorAdapter()
//        //        vendorAdapter.setGenerateDdl(true)
//        entityManager.jpaVendorAdapter = vendorAdapter
//        return entityManager
//    }
//
//
//    //
//    @Bean(name = ["adminTransactionProvider"])
//    fun transactionProvider(
//        @Qualifier("adminTransactionManager") txManager: PlatformTransactionManager
//    ): SpringTransactionProvider {
//        return SpringTransactionProvider(txManager)
//    }
//
//    @Bean
//    fun jooqConfiguration(
//        executeListenerProviders: ObjectProvider<ExecuteListenerProvider>,
//        configurationCustomizers: ObjectProvider<DefaultConfigurationCustomizer>,
//        properties: JooqProperties,
//        connectionProvider: ConnectionProvider,
//        @Qualifier("adminTransactionProvider") transactionProvider: TransactionProvider,
//    ): DefaultConfiguration {
//        val configuration = DefaultConfiguration()
//        configuration.set(properties.determineSqlDialect(adminDataSource()))
//        configuration.set(dataSourceConnectionProvider())
//        configuration.set(transactionProvider)
//        val toArray: Array<ExecuteListenerProvider> =
//            executeListenerProviders.orderedStream().toArray {
//                arrayOfNulls<ExecuteListenerProvider>(it)
//            }
//        configuration.set(*toArray)
//        configurationCustomizers.orderedStream().forEach { it.customize(configuration) }
//        return configuration
//    }
//
//    @Bean
//    @Order(0)
//    fun jooqExceptionTranslatorExecuteListenerProvider(): DefaultExecuteListenerProvider {
//        return DefaultExecuteListenerProvider(JooqExceptionTranslator())
//    }
//
//    @Bean
//    fun dslContext(configuration: org.jooq.Configuration): DefaultDSLContext {
//        return DefaultDSLContext(configuration)
//    }
}
