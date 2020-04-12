package com.weather.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@ConfigurationProperties(prefix = "climate.file")
@ConstructorBinding
public class ClimateFileConfig {

    private final Optional<String> location;

    @NotBlank
    private final String name;

    @NotBlank
    @Size(max = 1)
    private final char separator;

    public ClimateFileConfig(String location, String name, char separator) {
        this.location = Optional.ofNullable(location);
        this.name = name;
        this.separator = separator;
    }

    public String getName() {
        return name;
    }

    public char getSeparator() {
        return separator;
    }

    public String getFilePath() {
        return location.isPresent() ? (location.get().endsWith(System.lineSeparator()) ? location.get() + name : location.get() + System.lineSeparator() + name) : "/" + name;
    }

}
