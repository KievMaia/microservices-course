package br.com.kievmaia.bookservice.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder(toBuilder = true, setterPrefix = "with")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cambio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String from;
    private String to;
    private Double conversionFactor;
    private Double convertedValue;
    private String environment;
}
