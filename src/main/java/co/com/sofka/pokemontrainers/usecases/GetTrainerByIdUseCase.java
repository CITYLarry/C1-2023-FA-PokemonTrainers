package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import co.com.sofka.pokemontrainers.usecases.interfaces.IGetTrainerById;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetTrainerByIdUseCase implements IGetTrainerById {

    private final ITrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<TrainerDTO> getTrainer(String trnrId) {
        return trainerRepository
                .findById(trnrId)
                .switchIfEmpty(Mono.error(new RuntimeException("No trainer found for id " + trnrId)))
                .map(trnr -> modelMapper.map(trnr, TrainerDTO.class));
    }
}
