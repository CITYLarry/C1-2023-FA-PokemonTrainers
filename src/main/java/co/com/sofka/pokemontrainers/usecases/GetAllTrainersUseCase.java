package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import co.com.sofka.pokemontrainers.usecases.interfaces.IGetAllTrainers;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetAllTrainersUseCase implements IGetAllTrainers {

    private final ITrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<TrainerDTO> getAll() {
        return trainerRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(trnr -> modelMapper.map(trnr, TrainerDTO.class));
    }
}
