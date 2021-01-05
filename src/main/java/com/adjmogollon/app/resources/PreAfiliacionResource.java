package com.adjmogollon.app.resources;

import com.adjmogollon.app.controller.PreAfiliacionController;
import javax.ejb.Asynchronous;
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
@Path("preAfiliacion")
public class PreAfiliacionResource {
    
    @GET
    public Response ping(){
        return Response
                .ok("PreAfiliacion Mueve ok")
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
    public void PreAfiliacion(@Suspended
    final AsyncResponse asyncResponse, final PreAfiliacionController arg) throws Throwable {     
        asyncResponse.resume(doPreAfiliacion(arg));        
    }

    private Response doPreAfiliacion(PreAfiliacionController request) throws Throwable {
        request.preAfiliar();
        PreAfiliacionController response = new PreAfiliacionController();   
        if (request.isSuccess()) {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getMessage());
            return Response.ok().entity(response).build();
        } else {
            response.setSuccess(request.isSuccess());
            response.setMessage(request.getMessage());
            return Response.serverError().entity(response).build();
        }
        //return request;
    }
}
