package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.collection.Trainer;
import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import co.com.sofka.pokemontrainers.usecases.interfaces.IUpdateTrainer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateTrainerUseCase implements IUpdateTrainer {

    private final ITrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<TrainerDTO> update(String trnrId, TrainerDTO trainerDTO) {
        return trainerRepository
                .findById(trnrId)
                .switchIfEmpty(Mono.error(new RuntimeException("No trainer found for id " + trnrId)))
                .flatMap(trnr -> {
                    trainerDTO.setTrnrId(trnr.getTrnrId());
                    return trainerRepository.save(modelMapper.map(trainerDTO, Trainer.class));
                })
                .map(trnr ->modelMapper.map(trnr, TrainerDTO.class));
    }
}
