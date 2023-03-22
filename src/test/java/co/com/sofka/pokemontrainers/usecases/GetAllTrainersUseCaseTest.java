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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetAllTrainersUseCaseTest {

    @Mock
    ITrainerRepository trainerRepository;

    ModelMapper modelMapper;
    GetAllTrainersUseCase getAllTrainersUseCase;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        getAllTrainersUseCase = new GetAllTrainersUseCase(trainerRepository, modelMapper);
    }

    @Test
    @DisplayName("Get all trainers successfully")
    void successScenario() {

        Trainer t1 = new Trainer("testId1", "testName1", "testPokedollars1", List.of());
        Trainer t2 = new Trainer("testId2", "testName2", "testPokedollars2", List.of());

        Mockito.when(trainerRepository.findAll()).thenReturn(Flux.just(t1, t2));

        var result = getAllTrainersUseCase.getAll();

        StepVerifier.create(result)
                .expectNext(new TrainerDTO("testId1", "testName1", "testPokedollars1", List.of()))
                .expectNext(new TrainerDTO("testId2", "testName2", "testPokedollars2", List.of()))
                .verifyComplete();

        Mockito.verify(trainerRepository).findAll();
    }
}