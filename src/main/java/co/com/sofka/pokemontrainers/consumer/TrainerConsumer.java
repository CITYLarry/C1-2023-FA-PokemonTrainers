package co.com.sofka.pokemontrainers.consumer;

import co.com.sofka.pokemontrainers.config.RabbitMQConfig;
import co.com.sofka.pokemontrainers.usecases.AddPokemonToTeamUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrainerConsumer {

    private final ObjectMapper objectMapper;
    private final AddPokemonToTeamUseCase addPokemonToTeamUseCase;

    @RabbitListener(queues = RabbitMQConfig.TRAINERS_QUEUE)
    public void acceptPokemonEvent(String message) throws JsonProcessingException {
        PokemonEvent event = objectMapper.readValue(message, PokemonEvent.class);
        addPokemonToTeamUseCase.addToBelt(event.getTrnrId(), event.getPokemonToMove());
    }
}
