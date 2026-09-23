package pe.edu.cacaoselva.desktop.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pe.edu.cacaoselva.application.exception.ApiNoDisponibleException;
import pe.edu.cacaoselva.application.port.LoteDto;
import pe.edu.cacaoselva.application.port.LoteQueryPort;
import pe.edu.cacaoselva.desktop.config.HttpLoteClientProperties;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpLoteQueryAdapter implements LoteQueryPort {

    private final HttpClient httpClient;
    private final HttpLoteClientProperties properties;
    private final ObjectMapper objectMapper;

    public HttpLoteQueryAdapter(HttpLoteClientProperties properties) {
        this.properties = properties;

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(properties.timeout())
                .build();

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<LoteDto> obtenerLotes() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(properties.baseUrl() + "/lotes"))
                .timeout(properties.timeout())
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() >= 400) {
                throw new ApiNoDisponibleException(
                        "No se pudo consultar la API. Código HTTP: "
                                + response.statusCode()
                );
            }

            return objectMapper.readValue(
                    response.body(),
                    new TypeReference<List<LoteDto>>() {
                    }
            );

        } catch (InterruptedException exception) {

            Thread.currentThread().interrupt();

            throw new ApiNoDisponibleException(
                    "La consulta a la API fue interrumpida."
            );

        } catch (IOException exception) {

            throw new ApiNoDisponibleException(
                    "No se pudo conectar con la API."
            );
        }
    }
}