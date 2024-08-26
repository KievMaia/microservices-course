package br.com.kievmaia.cambioservice.controller;

import br.com.kievmaia.cambioservice.model.Cambio;
import br.com.kievmaia.cambioservice.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("cambio-service")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CambioController {

    private Logger logger = LoggerFactory.getLogger(CambioController.class);

    private final Environment environment;
    private final CambioRepository repository;

    @Operation(description = "Get cambio from currency")
    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount") BigDecimal amount,
                            @PathVariable("from") String from,
                            @PathVariable("to") String to) {
        logger.info("Getting cambio is called with -> {}, {}, {}", amount, from, to);
        final Cambio cambio = repository.findCambioByFromAndTo(from, to)
                .orElseThrow(() -> new RuntimeException("Currency Unsupported"));
        final var port = environment.getProperty("local.server.port");
        cambio.setEnvironment(port);
        final var conversionFactor = cambio.getConversionFactor();
        final var convertedValue = conversionFactor.multiply(amount);
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        return cambio;
    }
}
