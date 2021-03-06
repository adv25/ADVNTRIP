package org.dav95s.openNTRIP.Servers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dav95s.openNTRIP.Clients.User;
import org.dav95s.openNTRIP.Databases.Models.MountPointModel;
import org.dav95s.openNTRIP.Databases.Models.NtripCasterModel;
import org.dav95s.openNTRIP.Network.NetworkProcessor;
import org.dav95s.openNTRIP.ServerBootstrap;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NtripCaster {
    final static private Logger logger = LogManager.getLogger(NtripCaster.class.getName());

    final private ServerSocketChannel serverChannel;
    final private NtripCasterModel model;
    final private Set<MountPoint> mountPoints = new HashSet<>();

    public NtripCaster(NtripCasterModel model) throws IOException, SQLException {
        this.model = model;
        this.serverChannel = ServerSocketChannel.open();
        this.serverChannel.bind(new InetSocketAddress(model.getPort()));
        this.serverChannel.configureBlocking(false);

        NetworkProcessor.getInstance().registerServerChannel(serverChannel, this);

        ArrayList<Integer> mountpointsId = this.model.readMountpointsId();
        for (int id : mountpointsId) {
            mountPoints.add(new MountPoint(new MountPointModel(id)));
        }

        logger.info("NtripCaster :" + model.getPort() + " has been initiated!");
    }

    public void close() {
        try {
            ServerBootstrap.removeCaster(this);
            serverChannel.close();
        } catch (IOException e) {
            logger.warn(e);
        }
    }

    private byte[] sourceTable() {
        String header = "SOURCETABLE 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Connection: close\r\n";

        StringBuilder body = new StringBuilder();
        for (MountPoint mountPoint : this.mountPoints) {
            body.append(mountPoint.toString());
        }

        body.append("ENDSOURCETABLE\r\n");
        String bodyString = body.toString();
        header += "Content-Length: " + bodyString.getBytes().length + "\r\n\n";

        return (header + bodyString).getBytes();
    }

    /**
     * This method will be call on get request and after NMEA message from a client.
     *
     * @param user
     * @throws IOException
     */
    public void clientAuthorization(User user) throws IOException, SQLException {
        MountPoint mountPoint = this.getMountpoint(user.getHttpHeader("GET"));
        logger.debug(user.toString() + " requested mountpoint " + user.getHttpHeader("GET"));

        //The requested point does not exist. Send sourcetable.
        if (mountPoint == null) {
            user.write(ByteBuffer.wrap(sourceTable()));
            user.close();
            return;
        }

        user.setMountPoint(mountPoint);
        mountPoint.clientAuthorization(user);

        logger.debug("Caster " + model.getPort() + ": MountPoint " + user.getHttpHeader("GET") + " is not exists!");
    }

    private MountPoint getMountpoint(String name) throws IllegalArgumentException {
        for (MountPoint mp : this.mountPoints) {
            if (mp.getName().equals(name))
                return mp;
        }
        return null;
    }

    public int getId() {
        return model.getId();
    }
}
