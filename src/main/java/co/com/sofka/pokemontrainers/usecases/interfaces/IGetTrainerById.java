package co.com.sofka.pokemontrainers.usecases.interfaces;

import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface IGetTrainerById {
    Mono<TrainerDTO> getTrainer(String trnrId);
}
