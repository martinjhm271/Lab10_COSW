package co.edu.pdam.eci.persistenceapiintegration.ui.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.pdam.eci.persistenceapiintegration.R;
import co.edu.pdam.eci.persistenceapiintegration.data.DBException;
import co.edu.pdam.eci.persistenceapiintegration.data.Model;
import co.edu.pdam.eci.persistenceapiintegration.data.OrmModel;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import co.edu.pdam.eci.persistenceapiintegration.data.network.NetworkException;
import co.edu.pdam.eci.persistenceapiintegration.data.network.RequestCallback;
import co.edu.pdam.eci.persistenceapiintegration.data.network.RetrofitNetwork;
import co.edu.pdam.eci.persistenceapiintegration.data.ui.adapter.TeamsAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Model ormModel;
    ProgressDialog progressDialog;
    int progressBarStatus = 0;
    Handler progressBarbHandler = new Handler();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ormModel = new OrmModel();
        ormModel.init(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading URL teams ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setMax(1000);
        progressDialog.show();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressBarStatus < 100 ){
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBarStatus += 10;
                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setProgress(progressBarStatus);
                        }
                    });
                }
                progressDialog.dismiss();
            }
        });

        thread1.start();


        System.out.println("Second part");

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                RetrofitNetwork retrofitNetwork = new RetrofitNetwork();
                RequestCallback<List<Team>> listReceive = new RequestCallback<List<Team>>() {
                    @Override
                    public void onSuccess(List<Team> response) {
                        System.out.println("Test #2a: generated Response");
                        System.out.println(response);
                        for(Team team : response){
                            try {
                                ormModel.getTeamDao().createOrUpdate(team);
                            } catch (DBException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        System.out.println(e.getMessage());
                    }
                };
                retrofitNetwork.getTeams(listReceive);
            }
        });

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Team team1 = new Team("Manchester United","Manchestes","http://www.manutd.com/~/media/510AE241278B45FF97125DC1E1E32CBF.ashx");
        Team team2 = new Team("Inter De Milan","Inter","https://images-na.ssl-images-amazon.com/images/I/41L6NaxacsL._SX355_.jpg");

        try {
            ormModel.getTeamDao().createOrUpdate(team1);
            ormModel.getTeamDao().createOrUpdate(team2);
            System.out.println("Test #1: Creating classes");
            System.out.println(ormModel.getTeamDao().getAll());
        } catch (DBException e) {
            e.printStackTrace();
        }

        System.out.println("Test 2b: persistence DataBase");
        try {
            configureRecyclerView();
        } catch (DBException e) {
            e.printStackTrace();
        }

    }

    private void configureRecyclerView() throws DBException {
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter(new TeamsAdapter(ormModel.getTeamDao().getAll()));
    }
}

