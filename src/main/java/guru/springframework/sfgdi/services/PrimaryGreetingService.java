package guru.springframework.sfgdi.services;

import org.springframework.context.annotation.Primary;
public class PrimaryGreetingService implements GreetingService{

  @Override
  public String sayGreeting() {
    return "Hello World = From the PRIMARY Bean";
  }
  
}
