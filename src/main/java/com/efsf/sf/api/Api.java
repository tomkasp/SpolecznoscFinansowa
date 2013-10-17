package com.efsf.sf.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("")
public class Api{
    
    @GET
    @Path("/users")
    public Response getUsers() {
        return Response.status(200).entity("jest :D").build();
        
    }   
    
    @GET
    @Path("/odp")
    public Response getOdpo() {
        return Response.status(200).entity("jakas tam informacja").build();
        
    }  
}