package guru.springframework.sfgdi.config;

import guru.springframework.sfgdi.datasource.FakeDataSource;
import guru.springframework.sfgdi.repositories.EnglishGreetingRepository;
import guru.springframework.sfgdi.repositories.EnglishGreetingRepositoryImpl;
import guru.springframework.sfgdi.services.ConstructorInjectedGreetingService;
import guru.springframework.sfgdi.services.I18NEnglishService;
import guru.springframework.sfgdi.services.I18NSpanishService;
import guru.springframework.sfgdi.services.PropertyInjectedGreetingService;
import guru.springframework.sfgdi.services.SetterInjectedGreetingService;
import guru.springframework.sfgdi.services.PrimaryGreetingService;

import com.springframework.pets.PetService;
import com.springframework.pets.PetServiceFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by jt on 2/20/21.
 */
@PropertySource("classpath:datasource.properties")
@ImportResource("classpath:sfgdi-config.xml")
@Configuration
public class GreetingServiceConfig {

  @Bean
  FakeDataSource fakeDataSource(@Value("${guru.username}") String username, 
                                @Value("${guru.username}") String password, 
                                @Value("${guru.jdbcurl}") String jdbcurl) {
    FakeDataSource fakeDataSource = new FakeDataSource();
    fakeDataSource.setUsername(username);
    fakeDataSource.setPassword(password);
    fakeDataSource.setJdbcurl(jdbcurl);
    return fakeDataSource;
  }  

  @Bean
  PetServiceFactory petServieFactory(){
    return new PetServiceFactory();
  }

  @Profile({"dog", "default"})
  @Bean
  PetService dogPetService(PetServiceFactory petServiceFactory) {
    return petServiceFactory.getPetService("dog");
  }

  @Profile("cat")
  @Bean
  PetService catPetService(PetServiceFactory petServiceFactory) {
    return petServiceFactory.getPetService("cat");
  }

  @Bean
  EnglishGreetingRepository englishGreetingRepository() {
    return new EnglishGreetingRepositoryImpl();
  }
  
  @Profile("EN")
  @Bean("i18nService")
  I18NEnglishService i18NEnglishService(EnglishGreetingRepository englishGreetingRepository) {
    return new I18NEnglishService(englishGreetingRepository);
  }

  @Profile({"ES", "default"})
  @Bean("i18nService")
  I18NSpanishService i18NSpanishService() {
    return new I18NSpanishService();
  }

  @Bean
  @Primary
  PrimaryGreetingService primaryGreeetingService() {
    return new PrimaryGreetingService();
  }

  //@Bean
  ConstructorInjectedGreetingService constructorInjectedGreetingService(){
      return new ConstructorInjectedGreetingService();
  }

  @Bean
  PropertyInjectedGreetingService propertyInjectedGreetingService(){
      return new PropertyInjectedGreetingService();
  }

  @Bean
  SetterInjectedGreetingService setterInjectedGreetingService(){
      return new SetterInjectedGreetingService();
  }
}