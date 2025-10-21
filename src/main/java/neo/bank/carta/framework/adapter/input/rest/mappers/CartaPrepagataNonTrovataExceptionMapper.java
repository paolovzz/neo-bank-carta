package neo.bank.carta.framework.adapter.input.rest.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import neo.bank.carta.framework.adapter.input.rest.response.ErrorResponse;
import neo.bank.carta.application.exceptions.CartaPrepagataNonTrovataException;

@Provider
public class CartaPrepagataNonTrovataExceptionMapper implements ExceptionMapper<CartaPrepagataNonTrovataException> {

    @Override
    public Response toResponse(CartaPrepagataNonTrovataException exception) {
        ErrorResponse errorResponse = new ErrorResponse("[CARTA_PREPAGATA NOT FOUND]", exception.getMessage());
        return Response.status(404)
                       .entity(errorResponse)
                       .build();
    }
}
