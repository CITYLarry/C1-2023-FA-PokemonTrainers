package co.com.sofka.pokemontrainers.domain.collection;

import co.com.sofka.pokemontrainers.domain.dto.PokemonDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pokemon_trainers")
public class Trainer {

    @Id
    private String trnrId;
    @NotNull(message = "Trainer name is necessary")
    @NotBlank(message = "Trainer name is empty")
    private String name;
    @NotNull(message = "Money is necessary for starts a Pokemon adventure")
    @NotBlank(message = "Money is necessary for starts a Pokemon adventure")
    private String pokeDollar;
    private List<PokemonDTO> pokemonTeam = new ArrayList<>();
}
