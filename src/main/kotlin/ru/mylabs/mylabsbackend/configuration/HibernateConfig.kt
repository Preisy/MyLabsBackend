package ru.mylabs.mylabsbackend.configuration

import org.hibernate.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import ru.mylabs.mylabsbackend.utils.TaskFilesInterceptor

@Configuration
@EnableTransactionManagement
class HibernateConfig(private val taskFilesInterceptor: TaskFilesInterceptor) {
   // @Bean
    fun sessionFactory(): LocalSessionFactoryBean {
        val localSessionFactoryBean = LocalSessionFactoryBean()
        localSessionFactoryBean.setEntityInterceptor(taskFilesInterceptor)
        return localSessionFactoryBean
    }
}