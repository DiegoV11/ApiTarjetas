package com.example.tarjetaservice.Repository;

import com.example.tarjetaservice.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta,Integer> {

    Optional<Tarjeta> findByNumero (String numero);

}
