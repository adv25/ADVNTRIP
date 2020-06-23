package org.adv25.ADVNTRIP.Databases.Models;

import org.adv25.ADVNTRIP.Databases.DAO.BaseStationDAO;
import org.adv25.ADVNTRIP.Servers.BaseStation;
import org.adv25.ADVNTRIP.Spatial.Point;
import org.adv25.ADVNTRIP.Spatial.Point_lla;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseStationModel {
    private static Logger logger = LogManager.getLogger(BaseStationModel.class.getName());
    int id;
    String mountpoint;
    String identifier;
    String format;
    String format_details;
    int carrier;
    String nav_system;
    String country;
    Point_lla lla;
    int bitrate;
    String misc;
    boolean is_online;
    String password;
    Point ecef;
    int hz;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMountpoint() {
        return mountpoint;
    }

    public void setMountpoint(String mountpoint) {
        this.mountpoint = mountpoint;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat_details() {
        return format_details;
    }

    public void setFormat_details(String format_details) {
        this.format_details = format_details;
    }

    public int getCarrier() {
        return carrier;
    }

    public void setCarrier(int carrier) {
        this.carrier = carrier;
    }

    public String getNav_system() {
        return nav_system;
    }

    public void setNav_system(String nav_system) {
        this.nav_system = nav_system;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Point_lla getLla() {
        return lla;
    }

    public void setLla(Point_lla lla) {
        this.lla = lla;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Point getEcef() {
        return ecef;
    }

    public void setEcef(Point ecef) {
        this.ecef = ecef;
    }

    public int getHz() {
        return hz;
    }

    public void setHz(int hz) {
        this.hz = hz;
    }

    public void update() {
        BaseStationDAO dao = new BaseStationDAO();
        dao.update(this);
        logger.debug(mountpoint + " model has update.");
    }
}
