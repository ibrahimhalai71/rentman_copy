package com.rentmen.app.configurations;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  private LocalDate allowedEndDate = LocalDate.of(2024, 10, 25);
  private String key = "RntMn";
  
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public EntityManager getEntityManager() {
      return entityManager;
  }
  
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor((HandlerInterceptor)new ExpiryInterceptor(this.allowedEndDate, this.key));
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

}
