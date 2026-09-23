package pe.edu.cacaoselva.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pe.edu.cacaoselva.application.port.LoteRepository;
import pe.edu.cacaoselva.application.usecase.BuscarLotePorIdUseCase;
import pe.edu.cacaoselva.application.usecase.ContarLotesPendientesUseCase;
import pe.edu.cacaoselva.application.usecase.CrearLoteUseCase;
import pe.edu.cacaoselva.application.usecase.ListarLotesUseCase;

@Configuration
public class ApiBeansConfig {

    @Bean
    public ListarLotesUseCase listarLotesUseCase(
            LoteRepository repository) {

        return new ListarLotesUseCase(repository);
    }

    @Bean
    public BuscarLotePorIdUseCase buscarLotePorIdUseCase(
            LoteRepository repository) {

        return new BuscarLotePorIdUseCase(repository);
    }

    @Bean
    public ContarLotesPendientesUseCase contarLotesPendientesUseCase(
            LoteRepository repository) {

        return new ContarLotesPendientesUseCase(repository);
    }

    @Bean
    public CrearLoteUseCase crearLoteUseCase(
            LoteRepository repository) {

        return new CrearLoteUseCase(repository);
    }
}