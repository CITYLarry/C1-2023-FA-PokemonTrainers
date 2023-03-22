package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import co.com.sofka.pokemontrainers.usecases.interfaces.IDeleteTrainer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteTrainerUseCase implements IDeleteTrainer {

    private final ITrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<Void> delete(String trnrId) {
        return trainerRepository
                .findById(trnrId)
                .switchIfEmpty(Mono.error(new RuntimeException("No trainer found for id " + trnrId)))
                .flatMap(trnr -> trainerRepository.deleteById(trnr.getTrnrId()));
    }
}
