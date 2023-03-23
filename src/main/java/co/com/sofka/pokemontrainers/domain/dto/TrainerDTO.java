package co.com.sofka.pokemontrainers.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {

    private String trnrId;
    @NotNull(message = "Trainer name is necessary")
    @NotBlank(message = "Trainer name is empty")
    private String name;
    @NotNull(message = "Money is necessary for starts a Pokemon adventure")
    @NotBlank(message = "Money is necessary for starts a Pokemon adventure")
    private String pokeDollar;
    private List<PokemonDTO> pokemonTeam;
}
