package co.com.sofka.pokemontrainers.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pokemon_trainers")
public class Trainer {

    @Id
    private String trnrId;
    private String name;
    private String pokeDollar;
    private List<Pokemon> pokemonTeam;
}