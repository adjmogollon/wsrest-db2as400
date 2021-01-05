package com.adjmogollon.app.resources;

import com.adjmogollon.app.controller.AfiliacionController;
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
@Path("afiliacion")

public class AfiliacionResource {

    @GET
    public Response ping() {
        return Response
                .ok("Afiliacion Mueve ok")
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
    public void Afiliacion(@Suspended
            final AsyncResponse asyncResponse, final AfiliacionController arg) throws Throwable {
        asyncResponse.resume(doAfiliacion(arg));
    }

    private Response doAfiliacion(AfiliacionController request) throws Throwable {
        request.afiliar();
        AfiliacionController response = new AfiliacionController();
        if (request.isSuccess()) {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getMessage());
            return Response.ok().entity(response).build();
        } else {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getMessage());
            //return Response.serverError().entity(response).build();
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        //return request;
    }

}
