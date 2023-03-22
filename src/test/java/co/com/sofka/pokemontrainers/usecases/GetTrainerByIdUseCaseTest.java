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
class GetTrainerByIdUseCaseTest {

    @Mock
    ITrainerRepository trainerRepository;

    ModelMapper modelMapper;
    GetTrainerByIdUseCase getTrainerByIdUseCase;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        getTrainerByIdUseCase = new GetTrainerByIdUseCase(trainerRepository, modelMapper);
    }

    @Test
    @DisplayName("Get trainer by Id successfully")
    void successScenario() {

        Trainer t1 = new Trainer("testId", "testName", "testPokedollars", List.of());

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.just(t1));

        var result = getTrainerByIdUseCase.getTrainer("testId");

        StepVerifier.create(result)
                .expectNext(new TrainerDTO("testId", "testName", "testPokedollars", List.of()))
                .verifyComplete();

        Mockito.verify(trainerRepository).findById("testId");
    }

    @Test
    @DisplayName("Get trainer - triner not found")
    void failScenario() {

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.empty());

        var result = getTrainerByIdUseCase.getTrainer("testId");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("No trainer found for id testId"))
                .verify();

        Mockito.verify(trainerRepository).findById("testId");
    }
}