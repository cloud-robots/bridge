package cloud.robots.bridge.server.application

import cloud.robots.bridge.server.controller.ServerController
import cloud.robots.bridge.server.exceptions.ServerControllerAdvice
import cloud.robots.bridge.server.jpa.entity.Subscriber
import cloud.robots.bridge.server.jpa.entity.Topic
import cloud.robots.bridge.server.jpa.repository.SubscribersRepository
import cloud.robots.bridge.server.service.SubscriberService
import cloud.robots.bridge.server.service.SubscriberServiceImpl
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource




@Suppress("unused")
@Configuration
@EnableJpaRepositories(basePackageClasses = arrayOf(SubscribersRepository::class))
@EnableTransactionManagement
class ServerConfig {

  @Bean
  fun serverController(subscriberService: SubscriberService) = ServerController(subscriberService)

  @Bean
  fun serverControllerAdvice() = ServerControllerAdvice()

  @Bean
  @ConfigurationProperties(prefix="bridge.server.datasource.serverDB")
  fun serverDataSource(): DataSource = DataSourceBuilder.create().build()

  @Bean
  fun entityManagerFactory(
      builder: EntityManagerFactoryBuilder, serverDataSource: DataSource): LocalContainerEntityManagerFactoryBean =
      builder.dataSource(serverDataSource)
          .packages(Subscriber::class.java, Topic::class.java)
          .persistenceUnit("persistenceUnit")
          .build()

  @Bean
  fun subscriberService(subscribersRepository: SubscribersRepository) = SubscriberServiceImpl(subscribersRepository)
}
