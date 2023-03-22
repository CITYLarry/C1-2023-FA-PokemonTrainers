package co.com.sofka.pokemontrainers.router;

import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.usecases.SaveTrainerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TrainerRouter {

    @Bean
    public RouterFunction<ServerResponse> saveTrainer(SaveTrainerUseCase saveTrainerUseCase) {
        return route(
                POST("/trainers").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TrainerDTO.class)
                        .flatMap(trainerDTO -> saveTrainerUseCase.save(trainerDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.badRequest().build()))
        );
    }
}
