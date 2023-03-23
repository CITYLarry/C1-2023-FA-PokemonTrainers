package co.com.sofka.pokemontrainers.usecases;


import co.com.sofka.pokemontrainers.domain.collection.Trainer;
import co.com.sofka.pokemontrainers.domain.dto.PokemonDTO;
import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AddPokemonToTeamUseCaseTest {

    @Mock
    private ITrainerRepository trainerRepository;

    private ModelMapper modelMapper;
    private AddPokemonToTeamUseCase addPokemonToTeamUseCase;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        addPokemonToTeamUseCase = new AddPokemonToTeamUseCase(trainerRepository, modelMapper);
    }

    @Test
    @DisplayName("Add pokemon to trainer's team successfully")
    void successScenario() {

        Trainer t1 = new Trainer("testId", "testName", "testPokedollars", List.of());

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.just(t1));

        Trainer t2 = new Trainer(
                "testId",
                "testName",
                "testPokedollars",
                List.of(new PokemonDTO("pokemon1", "testNmbr", "testName", "testNick", List.of("testType"), true))
        );

        Mockito.when(trainerRepository.save(any(Trainer.class))).thenReturn(Mono.just(t2));

        PokemonDTO pokemonDTO = new PokemonDTO("testId", "testNmbr", "testName", "testNick", List.of("testType"), true);

        addPokemonToTeamUseCase.addToBelt("testId", pokemonDTO);

        StepVerifier.create(trainerRepository.save(t2))
                .expectNextMatches(trainer -> trainer.getPokemonTeam().size() == 1 &&
                        trainer.getPokemonTeam().get(0).getPkmnId().equals("pokemon1") &&
                        trainer.getPokemonTeam().get(0).getName().equals("testName") &&
                        trainer.getPokemonTeam().get(0).getInTeam().equals(true))
                .verifyComplete();

        Mockito.verify(trainerRepository).findById(any(String.class));
        Mockito.verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    @DisplayName("Add pokemon to trainer's team - trainer not found")
    void failScenario() {

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.empty());

        StepVerifier.create(trainerRepository.findById("testId"))
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(trainerRepository).findById("testId");
    }
}