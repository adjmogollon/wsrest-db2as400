package com.adjmogollon.app.resources;


import com.adjmogollon.app.controller.ConsultaSaldoCuentaPrincipalController;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author
 */
@Singleton
@Path("consultaSaldo")

public class ConsultaSaldoCuentaPrincipalResource {

    @GET
    public Response ping() {
        return Response
                .ok("Consulta Saldo Mueve ok")
                .build();
    }

    /**
     *
     * @param asyncResponse
     * @param arg
     * @throws java.lang.Throwable
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public void ConsultaSaldoCuentaPrincipal(@Suspended
            final AsyncResponse asyncResponse, final ConsultaSaldoCuentaPrincipalController arg) throws Throwable {
        asyncResponse.resume(doConsultaSaldoCuentaPrincipal(arg));
    }

    private Response doConsultaSaldoCuentaPrincipal(ConsultaSaldoCuentaPrincipalController request) throws Throwable {
        request.consultarSaldo();
        ConsultaSaldoCuentaPrincipalController response = new ConsultaSaldoCuentaPrincipalController();
        if (request.isSuccess()) {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getSaldoCuentaPrincipal());
            return Response.ok().entity(response).build();
        } else {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getMessage());
            return Response.serverError().entity(response).build();
        }

    }

}
