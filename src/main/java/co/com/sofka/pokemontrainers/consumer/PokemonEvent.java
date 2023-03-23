package co.com.sofka.pokemontrainers.consumer;

import co.com.sofka.pokemontrainers.domain.dto.PokemonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonEvent {

    private String trnrId;
    private PokemonDTO pokemonToMove;
    private String eventName; //moveToBelt, moveToPc
}
