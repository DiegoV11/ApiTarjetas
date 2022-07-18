package com.example.tarjetaservice.controller;

import com.example.tarjetaservice.Repository.TarjetaRepository;
import com.example.tarjetaservice.entity.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class TarjetaController {
    @Autowired
    TarjetaRepository tarjetaRepository;


    @GetMapping("/tarjeta")
    public List<Tarjeta> listaTarjetas(){
        return  tarjetaRepository.findAll();
    }


    @GetMapping("/tarjeta/{id}")
    public ResponseEntity<HashMap<String,Object>> obtenerTarjetaPorId(@PathVariable("id")String idStr){
        HashMap<String,Object> responseJson = new HashMap<>();
        try {
            Optional<Tarjeta> optionalTarjeta = tarjetaRepository.findById(Integer.parseInt(idStr));
            if(optionalTarjeta.isPresent()){
                responseJson.put("result","success");
                responseJson.put("tarjeta",optionalTarjeta.get());
                return ResponseEntity.ok(responseJson);
            }else{
                responseJson.put("msg","Tarjeta no encontrada");
            }
        }catch (NumberFormatException e){
            responseJson.put("msg","El id debe ser un número entero positivo");
        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);
    }

    @PostMapping("/tarjeta")
    public ResponseEntity<HashMap<String,Object>> guardarTarjeta(
            @RequestBody Tarjeta tarjeta){
        HashMap<String,Object> responseMap = new HashMap<>();
        System.out.println();
        Boolean condicion = tarjeta.getVerificacion()==3 && tarjeta.getNumero().length()==12;
        if (condicion){
            tarjetaRepository.save(tarjeta);
            responseMap.put("estado","creado");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
        }else {
            responseMap.put("estado","error");
            responseMap.put("msg","Datos incorrectos");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @PutMapping(value = "/tarjeta")
    public ResponseEntity<HashMap<String,Object>> actualizarTarjeta(@RequestBody Tarjeta tarjeta){
        HashMap<String, Object> responseMap = new HashMap<>();

        if (tarjeta.getId() != null && tarjeta.getId()>0){
            Optional<Tarjeta> opt = tarjetaRepository.findById(tarjeta.getId());
            if (opt.isPresent()){
                tarjetaRepository.save(tarjeta);
                responseMap.put("estado","actualizado");
                return ResponseEntity.ok(responseMap);
            }else {
                responseMap.put("estado","error");
                responseMap.put("msg","La tarjeta a actualizar no existe");
                return ResponseEntity.badRequest().body(responseMap);
            }
        }else{
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un ID");
            return ResponseEntity.badRequest().body(responseMap);
        }

    }




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
