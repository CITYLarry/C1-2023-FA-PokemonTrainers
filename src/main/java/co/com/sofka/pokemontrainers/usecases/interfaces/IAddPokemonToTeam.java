package co.com.sofka.pokemontrainers.usecases.interfaces;

import co.com.sofka.pokemontrainers.domain.dto.PokemonDTO;

@FunctionalInterface
public interface IAddPokemonToTeam {
    void addToBelt(String trnrId, PokemonDTO pokemonDTO);
}
