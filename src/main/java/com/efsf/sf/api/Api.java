package com.efsf.sf.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api")
public class Api{
    
    @GET
    @Path("/users")
    public Response getUsers() {
        return Response.status(200).entity("jest :D").build();
        
    }    
    
}