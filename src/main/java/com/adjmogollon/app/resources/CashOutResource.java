package com.adjmogollon.app.resources;

import com.adjmogollon.app.controller.CashOutController;
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
@Path("cashOut")
public class CashOutResource {
    
    @GET
    public Response ping(){
        return Response
                .ok("CashOut Mueve ok")
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
    public void CashOut(@Suspended
    final AsyncResponse asyncResponse, final CashOutController arg) throws Throwable {     
        asyncResponse.resume(doCashOut(arg));        
    }

    private Response doCashOut(CashOutController request) throws Throwable {
        request.EjecutarCashOut();
        CashOutController response = new CashOutController();
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
