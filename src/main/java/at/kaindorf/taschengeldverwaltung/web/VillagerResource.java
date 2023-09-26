package at.kaindorf.taschengeldverwaltung.web;

import at.kaindorf.taschengeldverwaltung.database.Access;
import at.kaindorf.taschengeldverwaltung.pojos.Booking;
import at.kaindorf.taschengeldverwaltung.pojos.TrustedPerson;
import at.kaindorf.taschengeldverwaltung.pojos.VillagerPerson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/villager")
public class VillagerResource {

    private Access access = Access.getTheInstance().getTheInstance();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllVillager")
    public Response getAllVillagers(){
        try {
            return Response.ok(access.getAllVillagers()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getVillager")
    public Response getVillager(@QueryParam("personId") Long personId) {
        try {
            return Response.ok(access.getVillagerById(personId)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTrustedPerson")
    public Response getTrustedPerson(@QueryParam("personId") Long villagerId) {
        try {
            return Response.ok(access.getPersonOfTrustByVillagerId(villagerId)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getVillagerBookingHistory")
    public Response getVillagerBookingHistory(@QueryParam("personId") Long personId) {
        try {
            return Response.ok(access.getVillagerBookingHistory(personId)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllBookingHistory")
    public Response getAllBookingHistory(@QueryParam("sortedBy") String sortedBy) {
        try {
            sortedBy="date";
            return Response.ok(access.getAllVillagersBookingHistory(sortedBy)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/postFastBooking")
    public Response postFastBooking(@QueryParam("personId") Long personId, Booking booking) {
        try {
            access.insertFastBooking(personId,booking);
            return Response.accepted().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/postUpdateVillager")
    public Response postUpdateVillager(VillagerPerson vp) {
        try {
            access.updateVillager(vp);
            return Response.accepted().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/postUpdateTrustedPerson")
    public Response postUpdateTrustedPerson(TrustedPerson tp) {
        try {
            access.updateTrustedPerson(tp);
            return Response.accepted().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getBalanceList")
    public Response getBalanceList() {

        try {

            return Response.ok(access.getBalanceList()).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAccountingJournal")
    public Response getAccountingJournal() {
        try {
            return Response.ok(access.getAccountingJournal(null)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getDepotInformations")
    public Response getDepotInformations(@QueryParam("personId") Long personId) {
        try {
            return Response.ok(access.getAccountingJournal(personId)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getDepotauskuenfteNegativ")
    public Response getDepotauskuenfteNegativ() {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllSalutations")
    public Response getTrustedPerson() {
        try {
            return Response.ok(access.getAllSalutations()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllRelations")
    public Response getAllRelations() {
        try {
            return Response.ok(access.getAllRelations()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllPurposes")
    public Response getAllPurposes() {
        try {
            return Response.ok(access.getAllPurposes()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllTransmissionMethods")
    public Response getAllTransmissionMethods() {
        try {
            return Response.ok(access.getAllTransmissionMethods()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getVillagerByShortSign")
    public Response getVillagerByShortSign(@QueryParam("pattern") String pattern) {
        try {
            return Response.ok(access.fastBookingSearch(pattern)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
    }

}
