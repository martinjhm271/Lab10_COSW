package co.edu.pdam.eci.persistenceapiintegration.data.network;

import co.edu.pdam.eci.persistenceapiintegration.data.DBException;

/**
 * Created by carlos on 13/04/18.
 */

public interface RequestCallback<T> {
    void onSuccess( T response );

    void onFailed( NetworkException e );
}
