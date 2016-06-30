package asyncapp.tornado.alex.asyncapp.network;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import asyncapp.tornado.alex.asyncapp.event.ActionEvent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Alex Kucherenko on 6/29/16.
 */
public class RestInteractionWorker {

    RestServerInterface restInterface;

    public RestInteractionWorker() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restInterface = retrofit.create(RestServerInterface.class);
    }

    public void perfomServerOperation() {
        restInterface.listUsers()
                .subscribeOn(Schedulers.newThread())
                .timeout(30, TimeUnit.SECONDS)
                .map(new Func1<List<User>, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(List<User> users) {
                        return new ActionEvent(true, users.get(0).getLogin());
                    }
                })
                .onErrorReturn(new Func1<Throwable, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(Throwable throwable) {
                        return new ActionEvent(false, "failed");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ActionEvent>() {
                    @Override
                    public void call(ActionEvent event) {
                        EventBus.getDefault().post(event);
                    }
                });
    }
}