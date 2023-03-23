package co.com.sofka.pokemontrainers.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PokemonDTO {

    private String pkmnId;
    private String pkdxNumber;
    private String name;
    private String nickname;
    private List<String> typeList;
    private Boolean inTeam;
}
