package co.edu.pdam.eci.persistenceapiintegration.data.network;



public class NetworkException extends Exception {
    public NetworkException( String detailMessage, Throwable throwable ) {
        super( detailMessage, throwable );
    }
}
