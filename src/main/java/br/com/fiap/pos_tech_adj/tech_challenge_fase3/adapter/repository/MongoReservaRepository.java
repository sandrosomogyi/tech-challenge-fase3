package br.com.fiap.pos_tech_adj.tech_challenge_fase3.adapter.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.Reserva;
import br.com.fiap.pos_tech_adj.tech_challenge_fase3.domain.repository.ReservaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MongoReservaRepository extends MongoRepository<Reserva, String>, ReservaRepository {

    List<Reserva> findByRestauranteId(String restauranteId);
    List<Reserva> findByClienteId(String clienteId);

    @Query("{ 'dataHoraReserva' : { $gte: ?0, $lt: ?1 } }")
    List<Reserva> findByDataHoraReservaBetween(LocalDateTime start, LocalDateTime end);

}
