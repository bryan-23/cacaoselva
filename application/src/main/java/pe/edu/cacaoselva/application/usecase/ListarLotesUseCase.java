package pe.edu.cacaoselva.application.usecase;
import pe.edu.cacaoselva.application.port.LoteRepository; import pe.edu.cacaoselva.domain.model.Lote; import java.util.List;
public class ListarLotesUseCase { private final LoteRepository loteRepository; public ListarLotesUseCase(LoteRepository loteRepository){this.loteRepository=loteRepository;} public List<Lote> ejecutar(){return loteRepository.findAll();} }
