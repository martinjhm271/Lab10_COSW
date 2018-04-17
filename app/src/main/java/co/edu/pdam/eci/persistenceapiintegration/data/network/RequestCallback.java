package co.edu.pdam.eci.persistenceapiintegration.data.network;

import co.edu.pdam.eci.persistenceapiintegration.data.DBException;



public interface RequestCallback<T> {
    void onSuccess( T response );

    void onFailed( NetworkException e );
}
