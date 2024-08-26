package br.com.kievmaia.greetingservice.controller;

import br.com.kievmaia.greetingservice.configuration.GreetingConfiguration;
import br.com.kievmaia.greetingservice.model.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GreetingController {
    private static final String template = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final GreetingConfiguration configuration;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "") String name) {
        if (name.isEmpty()) {
            name = configuration.getDefaultValue();
        }
        return new Greeting(
                counter.incrementAndGet(),
                String.format(template, configuration.getGreeting(), name)
        );
    }
}