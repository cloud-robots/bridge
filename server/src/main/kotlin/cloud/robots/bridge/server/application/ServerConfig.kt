package cloud.robots.bridge.server.application

import cloud.robots.bridge.server.controller.ServerController
import cloud.robots.bridge.server.exceptions.ServerControllerAdvice
import cloud.robots.bridge.server.jpa.entity.Subscriber
import cloud.robots.bridge.server.jpa.repository.SubscribersRepository
import cloud.robots.bridge.server.service.SubscriberService
import cloud.robots.bridge.server.service.SubscriberServiceImpl
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource


@Suppress("unused")
@Configuration
@EnableJpaRepositories(basePackageClasses = arrayOf(SubscribersRepository::class))
class ServerConfig {

  @Bean
  fun serverController(subscriberService: SubscriberService) = ServerController(subscriberService)

  @Bean
  fun serverControllerAdvice() = ServerControllerAdvice()

  @Bean
  fun serverDataSource(): DataSource = EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.HSQL)
      .build()

  @Bean
  fun entityManagerFactory(
      builder: EntityManagerFactoryBuilder, serverDataSource: DataSource): LocalContainerEntityManagerFactoryBean =
      builder.dataSource(serverDataSource)
          .packages(Subscriber::class.java)
          .persistenceUnit("persistenceUnit")
          .build()

  @Bean
  fun subscriberService(subscribersRepository: SubscribersRepository) = SubscriberServiceImpl(subscribersRepository)
}
