package co.edu.pdam.eci.persistenceapiintegration.data.Service;

import java.util.List;

import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by carlos on 13/04/18.
 */

public interface TeamsService {
    @GET( "teams.json" )
    Call<List<Team>> getTeamsList( );
}
