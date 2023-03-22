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
class SaveTrainerUseCaseTest {

    @Mock
    ITrainerRepository trainerRepository;

    ModelMapper modelMapper;
    SaveTrainerUseCase saveTrainerUseCase;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        saveTrainerUseCase = new SaveTrainerUseCase(trainerRepository, modelMapper);
    }

    @Test
    @DisplayName("Save trainer successfully")
    void successScenario() {

        Trainer t1 = new Trainer("testId", "testName", "testPokedollars", List.of());

        Mockito.when(trainerRepository.save( new Trainer("testId", "testName", "testPokedollars", List.of())))
                .thenReturn(Mono.just(t1));

        var result = saveTrainerUseCase.save(modelMapper.map(t1, TrainerDTO.class));

        StepVerifier.create(result)
                .expectNext(new TrainerDTO("testId", "testName", "testPokedollars", List.of()))
                .verifyComplete();
    }
}