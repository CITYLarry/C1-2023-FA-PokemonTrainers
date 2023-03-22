package co.com.sofka.pokemontrainers.usecases.interfaces;

import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface IGetAllTrainers {
    Flux<TrainerDTO> getAll();
}
