package org.fundacionparaguaya.advisorapp.data.remote;

/**
 * A remote server API that can be connected to.
 */
public class Server {
    private String host;
    private int port;
    private String name;

    Server(String host, int port, String name) {
        this.host = host;
        this.port = port;
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }
}
