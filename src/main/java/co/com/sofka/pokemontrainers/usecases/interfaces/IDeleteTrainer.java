package co.com.sofka.pokemontrainers.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface IDeleteTrainer {
    Mono<Void> delete(String trnrId);
}
