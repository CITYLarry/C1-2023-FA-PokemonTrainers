package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.collection.Trainer;
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
class DeleteTrainerUseCaseTest {

    @Mock
    ITrainerRepository trainerRepository;

    ModelMapper modelMapper;
    DeleteTrainerUseCase deleteTrainerUseCase;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        deleteTrainerUseCase = new DeleteTrainerUseCase(trainerRepository, modelMapper);
    }

    @Test
    @DisplayName("Delete trainer successfully")
    void successScenario() {

        Trainer t1 = new Trainer("testId", "testName", "testPokedollars", List.of());

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.just(t1));
        Mockito.when(trainerRepository.deleteById("testId")).thenReturn(Mono.empty());

        var result = deleteTrainerUseCase.delete("testId");

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        Mockito.verify(trainerRepository).findById("testId");
        Mockito.verify(trainerRepository).deleteById("testId");
    }

    @Test
    @DisplayName("Delete trainer - trainer not found")
    void failScenario() {

        Mockito.when(trainerRepository.findById("testId")).thenReturn(Mono.empty());

        var result = deleteTrainerUseCase.delete("testId");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("No trainer found for id testId"))
                .verify();

        Mockito.verify(trainerRepository).findById("testId");
    }
}