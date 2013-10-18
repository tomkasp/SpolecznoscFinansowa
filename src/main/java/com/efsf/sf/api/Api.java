package com.efsf.sf.api;

import com.efsf.sf.util.Security;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class Api {

    private String key2 = "2580e6b83829012355145f2ce86b940c";

    @POST
    @Path("/paymentStatusChanged")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendEmail(@FormParam("pos_id") String pos_id, @FormParam("session_id") String session_id,
            @FormParam("ts") String ts, @FormParam("sig") String sig) {

        System.out.println(pos_id + " | " + session_id);

        if (Security.md5(pos_id + session_id + ts + key2).equals(sig)) {
            System.out.println("========================> OK");
            return Response.ok("OK").build();
        } else {
            System.out.println("========================> ERROR");
            return Response.ok("ERROR").build();
        }
    }

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        System.out.println("TEST");
        return Response.ok("TEST").build();
    }
}
