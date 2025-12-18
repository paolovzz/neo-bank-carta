package neo.bank.carta.framework.adapter.input.rest.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import neo.bank.carta.domain.exceptions.ValidazioneException;
import neo.bank.carta.framework.adapter.input.rest.model.ErrorResponse;

@Provider
@Slf4j
public class ValidazioneExceptionMapper implements ExceptionMapper<ValidazioneException> {

    @Override
    public Response toResponse(ValidazioneException exception) {
        log.error("Input non valido", exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return Response.status(400)
                       .entity(errorResponse)
                       .build();
    }
}
