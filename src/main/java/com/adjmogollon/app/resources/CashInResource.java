package com.adjmogollon.app.resources;

import com.adjmogollon.app.controller.CashInController;
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
@Path("cashIn")
public class CashInResource {

    @GET
    public Response ping() {
        return Response
                .ok("CashIn Mueve Ok")
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
    public void CashIn(@Suspended
            final AsyncResponse asyncResponse, final CashInController arg) throws Throwable {
        asyncResponse.resume(doCashIn(arg));
    }

    private Response doCashIn(CashInController request) throws Throwable {
        request.EjecutarCashIn();
        CashInController response = new CashInController();
        if (request.isSuccess()) {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getMessage());
            response.setReferencia(request.getReferencia());
            return Response.ok().entity(response).build();
        } else {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getMessage());
            return Response.serverError().entity(response).build();
        }

    }
}
