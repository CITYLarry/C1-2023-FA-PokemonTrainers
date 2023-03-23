package co.com.sofka.pokemontrainers.usecases;

import co.com.sofka.pokemontrainers.domain.dto.PokemonDTO;
import co.com.sofka.pokemontrainers.repository.ITrainerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RemovePokemonFromTeamUseCase {

    private final ITrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    public void transferToPc(String trnrId, PokemonDTO pokemonDTO) {
        trainerRepository
                .findById(trnrId)
                .switchIfEmpty(Mono.error(new RuntimeException("Trainer not found for id " + trnrId)))
                .flatMap(trainer -> {
                    var pokemonInTeam = trainer.getPokemonTeam();
                    pokemonInTeam.removeIf(pkmn -> pkmn.getPkmnId().equals(pokemonDTO.getPkmnId()));
                    trainer.setPokemonTeam(pokemonInTeam);
                    return trainerRepository.save(trainer);
                })
                .subscribe();
    }
}
