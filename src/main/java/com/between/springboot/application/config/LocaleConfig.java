package com.between.springboot.application.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;

@Configuration
public class LocaleConfig {

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename("messages");
    source.setDefaultEncoding("UTF-8");
    return source;
  }

  @Bean
  public LocaleContextResolver localeResolver() {
    AcceptHeaderLocaleContextResolver localeResolver = new AcceptHeaderLocaleContextResolver();
    localeResolver.setDefaultLocale(Locale.forLanguageTag("es"));
    return localeResolver;
  }
}
