package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.dto.PokemonDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import co.com.sofka.pokemontrainers.usecases.interfaces.IAddPokemonToTeam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddPokemonToTeamUseCase implements IAddPokemonToTeam {

    private final ITrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addToBelt(String trnrId, PokemonDTO pokemonDTO) {
        trainerRepository
                .findById(trnrId)
                .switchIfEmpty(Mono.error(new RuntimeException("Trainer not found for id " + trnrId)))
                .flatMap(trainer -> {
                    var pokemonInTeam = trainer.getPokemonTeam();
                    pokemonInTeam.add(pokemonDTO);
                    trainer.setPokemonTeam(pokemonInTeam);
                    return trainerRepository.save(trainer);
                })
                .subscribe();
    }
}
