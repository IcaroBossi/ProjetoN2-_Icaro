package icaro.resource;

import icaro.api.Trends;
import icaro.dao.TrendsDao;
import icaro.api.Results;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("trends")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class TrendsResource {
    TrendsDao dao;

    public TrendsResource(TrendsDao dao){
        this.dao = dao;
    }

    @GET
    public Trends getAllTrends(){
        return dao.getAllTrends();
    }

    @POST
    @Path("/create/{data}")
    public void createResult(@PathParam("data") String data){
        dao.createResult(getResultFromString(data));
    }

    @PUT
    @Path("/update/{data}")
    public void updateResult(@PathParam("data") String data){
        dao.updateResult(getResultFromString(data));
    }

    @DELETE
    @Path("/delete/{data}")
    public void deleteResult(@PathParam("data") String data){
        dao.deleteResult(getResultFromString(data));
    }

    public Results getResultFromString(String data){
        String[] cols = data.split(",");
        Results result = new Results();
        result.setDate(cols[0]);
        result.setValue(Double.parseDouble(cols[1]));
        return result;
    }

}
