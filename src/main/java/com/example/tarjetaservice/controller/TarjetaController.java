package com.example.tarjetaservice.controller;

import com.example.tarjetaservice.Repository.TarjetaRepository;
import com.example.tarjetaservice.entity.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class TarjetaController {
    @Autowired
    TarjetaRepository tarjetaRepository;

    @PostMapping("/verificacion")
    public ResponseEntity<HashMap<String, Object>> verificationCard(@RequestBody Tarjeta tarjeta){
        HashMap<String, Object> responseJson = new HashMap<>();
        Optional <Tarjeta> optionalTarjeta = tarjetaRepository.findByNumero(tarjeta.getNumero());
        if (optionalTarjeta.isPresent()){
            Tarjeta tarjeta1 = optionalTarjeta.get();
            LocalDate vencimiento = tarjeta1.getVencimiento();
            String mes = String.valueOf(vencimiento.getMonth());
            String anho = String.valueOf(vencimiento.getYear());
            Boolean boleano = !tarjeta1.getApellidos().equalsIgnoreCase(tarjeta.getApellidos())
                    || !tarjeta1.getNombre().equalsIgnoreCase(tarjeta.getNombre())
                    || !tarjeta1.getCorreo().equalsIgnoreCase(tarjeta.getCorreo())
                    || !vencimiento.getMonth().equals(tarjeta.getVencimiento().getMonth())
                    || !(vencimiento.getYear() == tarjeta.getVencimiento().getYear())
                    || !(tarjeta1.getVerificacion() == tarjeta.getVerificacion());

            if(!boleano){
                responseJson.put("result","success");
                return ResponseEntity.ok(responseJson);
            }
            }
            responseJson.put("result","failure");
            return ResponseEntity.badRequest().body(responseJson);
        }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionExcepcion(HttpServletRequest request) {

        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Formato incorrecto");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }
}
