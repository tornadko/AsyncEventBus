package asyncapp.tornado.alex.asyncapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import asyncapp.tornado.alex.asyncapp.event.ActionEvent;
import asyncapp.tornado.alex.asyncapp.network.RestInteractionWorker;

/**
 * Created by Alex Kucherenko on 6/29/16.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUsersFromServer();
            }
        });

    }

    private void requestUsersFromServer() {
        new RestInteractionWorker().perfomServerOperation();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActionEvent(ActionEvent event) {
        Log.d("TAG", "got event:" + event.getMessage());
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_LONG).show();
    }
}
