package br.com.resource;

import br.com.service.CorreioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Slf4j
@Path("/correios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CorreioResource {

    @Inject
    CorreioService correioService;

    @GET
    @Path("/consultarCep/{cep}")
    public String consultarCep(@PathParam("cep") String cep) {
        try {
            return new ObjectMapper().writeValueAsString(correioService.consultarCep(cep));
        } catch (Exception e) {
            log.error("Erro ao consultar o cep {}", e.getMessage(), e);
        }
        return null;
    }

    @GET
    @Path("/rastrear/pacote/{codigo}")
    public String ratrearPacote(@PathParam("codigo") String codigo) {
        try {
            return new ObjectMapper().writeValueAsString(correioService.ratrearPacote(codigo));
        } catch (Exception e) {
            log.error("Erro ao rastrear pacote {}", e.getMessage(), e);
        }
        return null;
    }
}
