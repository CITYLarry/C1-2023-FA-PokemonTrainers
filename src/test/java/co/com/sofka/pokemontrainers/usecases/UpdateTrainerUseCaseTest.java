package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.collection.Trainer;
import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UpdateTrainerUseCaseTest {

    @Mock
    ITrainerRepository trainerRepository;

    ModelMapper modelMapper;
    UpdateTrainerUseCase updateTrainerUseCase;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        updateTrainerUseCase = new UpdateTrainerUseCase(trainerRepository, modelMapper);
    }

    @Test
    @DisplayName("Update trainer successfully")
    void successScenario() {

        Trainer t1 = new Trainer("testId", "testName", "testPokedollars", List.of());

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.just(t1));

        Trainer t2 = new Trainer("testId", "testName2", "testPokedollars2", List.of());

        Mockito.when(trainerRepository.save(t2)).thenReturn(Mono.just(t2));

        var result = updateTrainerUseCase.update(
                "testId",
                new TrainerDTO("testId", "testName2", "testPokedollars2", List.of())
        );

        StepVerifier.create(result)
                .expectNext(new TrainerDTO("testId", "testName2", "testPokedollars2", List.of()))
                .verifyComplete();

        Mockito.verify(trainerRepository).findById("testId");
    }

    @Test
    @DisplayName("Update trainer - trainer not found")
    void failScenario() {

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.empty());

        var result = updateTrainerUseCase.update("testId", new TrainerDTO());

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("No trainer found for id testId"))
                .verify();

        Mockito.verify(trainerRepository).findById("testId");
    }
}