package co.com.sofka.pokemontrainers.repository;

import co.com.sofka.pokemontrainers.domain.collection.Trainer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrainerRepository extends ReactiveMongoRepository<Trainer, String> {
}
