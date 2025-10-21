package neo.bank.carta.framework.adapter.input.rest.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import neo.bank.carta.application.exceptions.CartaNonTrovataException;
import neo.bank.carta.framework.adapter.input.rest.response.ErrorResponse;

@Provider
public class CartaNonTrovataExceptionMapper implements ExceptionMapper<CartaNonTrovataException> {

    @Override
    public Response toResponse(CartaNonTrovataException exception) {
        ErrorResponse errorResponse = new ErrorResponse("[CARTA_PREPAGATA NOT FOUND]", exception.getMessage());
        return Response.status(404)
                       .entity(errorResponse)
                       .build();
    }
}
