package asyncapp.tornado.alex.asyncapp.network;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Alex Kucherenko on 6/29/16.
 */
public interface RestServerInterface {

    @GET("users")
    Observable<List<User>> listUsers();
}