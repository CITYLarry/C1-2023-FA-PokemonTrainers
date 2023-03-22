package co.com.sofka.pokemontrainers.domain.dto;

import co.com.sofka.pokemontrainers.domain.collection.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {

    private String trnrId;
    private String name;
    private String pokeDollar;
    private List<Pokemon> pokemonTeam;
}
