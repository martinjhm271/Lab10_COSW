package co.edu.pdam.eci.persistenceapiintegration.data.Service;

import java.util.List;

import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import retrofit2.Call;
import retrofit2.http.GET;



public interface TeamsService {
    @GET( "teams.json" )
    Call<List<Team>> getTeamsList( );
}
