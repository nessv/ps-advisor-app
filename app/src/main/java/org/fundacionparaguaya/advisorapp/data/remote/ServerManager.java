package org.fundacionparaguaya.advisorapp.data.remote;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A manager for choosing which server to be connected to.
 */

@Singleton
public class ServerManager {
    public static final String TAG = "ServerManager";
    private static final String PREFS_SERVER = "server";
    private static final String KEY_HOST = "host";
    private static final String KEY_PORT = "port";

    private SharedPreferences mPreferences;
    private MutableLiveData<Server> mSelected;
    private List<Server> mServers;


    public ServerManager(Application application) {
        mPreferences = application.getApplicationContext()
                .getSharedPreferences(PREFS_SERVER, MODE_PRIVATE);

        mServers = new ArrayList<>(3);
        mServers.add(new Server("povertystoplightiqp.org", 8080, "WPI Server"));
        mServers.add(new Server("testing.povertstoplight.org", 80, "Development Server"));
        mServers.add(new Server("povertystoplightiqp.org", 80, "Production"));

        mSelected = new MutableLiveData<>();
        mSelected.setValue(loadServerSelection());
    }

    public LiveData<Server> getSelected() {
        return mSelected;
    }

    public Server getSelectedNow() {
        return mSelected.getValue();
    }

    public void setSelected(Server selected) {
        this.mSelected.postValue(selected);
    }

    public List<Server> getServers() {
        return mServers;
    }

    private Server loadServerSelection() {
        Server defaultServer = mServers.get(0);
        String host = mPreferences.getString(KEY_HOST, defaultServer.getHost());
        int port = mPreferences.getInt(KEY_PORT, defaultServer.getPort());
        for (Server server : mServers) {
            if (server.getHost().equals(host)
                    && server.getPort() == port) {
                return server;
            }
        }
        return null;
    }

    private void saveServerSelection() {
        Server selected = mSelected.getValue();
        if (selected == null) {
            Log.w(TAG, "saveServerSelection: Attempted to save a null selected server!");
            return;
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_HOST, selected.getHost());
        editor.putInt(KEY_PORT, selected.getPort());
        editor.apply();
    }
}
