package co.com.sofka.pokemontrainers.consumer;

import co.com.sofka.pokemontrainers.config.RabbitMQConfig;
import co.com.sofka.pokemontrainers.usecases.AddPokemonToTeamUseCase;
import co.com.sofka.pokemontrainers.usecases.RemovePokemonFromTeamUseCase;
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
    private final RemovePokemonFromTeamUseCase removePokemonFromTeamUseCase;

    @RabbitListener(queues = RabbitMQConfig.TRAINERS_QUEUE)
    public void acceptPokemonEvent(String message) throws JsonProcessingException {
        PokemonEvent event = objectMapper.readValue(message, PokemonEvent.class);
        if (event.getEventName().equals("Move to belt")) addPokemonToTeamUseCase.addToBelt(event.getTrnrId(), event.getPokemonToMove());
        if (event.getEventName().equals("Transfer to pc")) removePokemonFromTeamUseCase.transferToPc(event.getTrnrId(), event.getPokemonToMove());
    }
}
