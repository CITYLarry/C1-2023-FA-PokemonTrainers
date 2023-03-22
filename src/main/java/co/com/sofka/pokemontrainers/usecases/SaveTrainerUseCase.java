package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.collection.Trainer;
import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import co.com.sofka.pokemontrainers.usecases.interfaces.ISaveTrainer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveTrainerUseCase implements ISaveTrainer {

    private final ITrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<TrainerDTO> save(TrainerDTO trainerDTO) {
        return trainerRepository
                .save(modelMapper.map(trainerDTO, Trainer.class))
                .map(trnr -> modelMapper.map(trnr, TrainerDTO.class));
    }
}
