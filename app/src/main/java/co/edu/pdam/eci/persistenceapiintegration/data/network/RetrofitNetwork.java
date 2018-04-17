package co.edu.pdam.eci.persistenceapiintegration.data.network;

import java.io.IOException;
import java.util.List;

import co.edu.pdam.eci.persistenceapiintegration.data.Service.TeamsService;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import co.edu.pdam.eci.persistenceapiintegration.ui.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitNetwork {

    private static final String BASE_URL = "https://raw.githubusercontent.com/sancarbar/starting-android-lists/master/";

    private TeamsService teamsService;
    public MainActivity m;

    public RetrofitNetwork(MainActivity m) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        teamsService = retrofit.create( TeamsService.class );
        this.m=m;
    }

    public void getTeams( RequestCallback<List<Team>> requestCallback ) {
        try {
            Call<List<Team>> call = teamsService.getTeamsList( );
            Response<List<Team>> execute = call.execute();
            requestCallback.onSuccess( execute.body() );
        } catch ( IOException e ) {
            requestCallback.onFailed( new NetworkException( e.getMessage(), null ) );
        }
    }
}
