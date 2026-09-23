package pe.edu.cacaoselva.desktop.config;

import java.time.Duration;

public record HttpLoteClientProperties(
        String baseUrl,
        Duration timeout
) {
}
