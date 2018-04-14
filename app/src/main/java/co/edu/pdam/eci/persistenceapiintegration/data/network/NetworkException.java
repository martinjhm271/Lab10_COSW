package co.edu.pdam.eci.persistenceapiintegration.data.network;

/**
 * Created by carlos on 13/04/18.
 */

public class NetworkException extends Exception {
    public NetworkException( String detailMessage, Throwable throwable ) {
        super( detailMessage, throwable );
    }
}
