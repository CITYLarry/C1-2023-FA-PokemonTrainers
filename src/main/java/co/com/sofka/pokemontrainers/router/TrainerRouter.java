package co.com.sofka.pokemontrainers.router;

import co.com.sofka.pokemontrainers.domain.dto.TrainerDTO;
import co.com.sofka.pokemontrainers.usecases.DeleteTrainerUseCase;
import co.com.sofka.pokemontrainers.usecases.GetAllTrainersUseCase;
import co.com.sofka.pokemontrainers.usecases.GetTrainerByIdUseCase;
import co.com.sofka.pokemontrainers.usecases.SaveTrainerUseCase;
import co.com.sofka.pokemontrainers.usecases.UpdateTrainerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
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
    public RouterFunction<ServerResponse> getAllTrainers(GetAllTrainersUseCase getAllTrainersUseCase) {
        return route(
                GET("/trainers"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllTrainersUseCase.getAll(), TrainerDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getTrainerById(GetTrainerByIdUseCase getTrainerByIdUseCase) {
        return route(
                GET("/trainers/{trnrId}"),
                request -> getTrainerByIdUseCase.getTrainer(request.pathVariable("trnrId"))
                        .flatMap(trainerDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(trainerDTO))
                        .onErrorResume(throwable -> ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(throwable.getMessage()))
        );
    }

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

    @Bean
    public RouterFunction<ServerResponse> updateTrainer(UpdateTrainerUseCase updateTrainerUseCase) {
        return route(
                PUT("/trainers/{trnrId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TrainerDTO.class)
                        .flatMap(trainerDTO -> updateTrainerUseCase.update(request.pathVariable("trnrId"), trainerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.badRequest()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteTrainer(DeleteTrainerUseCase deleteTrainerUseCase) {
        return route(
                DELETE("/trainers/{trnrId}"),
                request -> deleteTrainerUseCase.delete(request.pathVariable("trnrId"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Deleted trainer with id: " + request.pathVariable("trnrId")))
                        .flatMap(response -> response)
                        .onErrorResume(throwable -> ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(throwable.getMessage()))
        );
    }
}
