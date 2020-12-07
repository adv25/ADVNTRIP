import org.dav95s.openNTRIP.Clients.Passwords.PasswordHandler;
import org.dav95s.openNTRIP.Databases.Models.UserModel;
import org.dav95s.openNTRIP.Databases.Models.MountPointModel;
import org.dav95s.openNTRIP.Databases.Models.NtripCasterModel;
import org.dav95s.openNTRIP.Databases.Models.ReferenceStationModel;
import org.dav95s.openNTRIP.Tools.Config;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestDB {
    @Test
    public void casters() throws SQLException {
        NtripCasterModel model = new NtripCasterModel();
        model.setAddress("localhost");
        model.setGroup_id(1);
        model.setPort(2101);
        model.setStatus(false);
        model.create();

        model.setPort(44444);
        Assert.assertTrue(model.read());
        Assert.assertEquals(2101, model.getPort());

        model.setPort(123);
        Assert.assertTrue(model.update());

        Assert.assertTrue(model.read());
        Assert.assertEquals(123, model.getPort());

        Assert.assertTrue(model.delete());
        Assert.assertFalse(model.read());
    }

    @Test
    public void refstations() throws SQLException {
        ReferenceStationModel model = new ReferenceStationModel();
        model.setName("dadaf");
        model.setPassword("123");
        model.setFormat("123");
        model.create();
        model.setFormat("321");

        Assert.assertTrue(model.read());
        Assert.assertEquals("123", model.getFormat());

        model.setFormat("321");
        Assert.assertTrue(model.update());
        model.setFormat("4444");
        Assert.assertTrue(model.read());
        Assert.assertEquals("321", model.getFormat());

        Assert.assertTrue(model.delete());
        Assert.assertFalse(model.read());
    }

    @Test
    public void mountpoints() throws SQLException {
        MountPointModel model = new MountPointModel();
        model.setName("ref");
        model.setCaster_id(4);

        model.setFormat("123");
        model.create();
        model.setFormat("321");

        Assert.assertTrue(model.read());
        Assert.assertEquals("123", model.getFormat());

        model.setFormat("321");
        Assert.assertTrue(model.update());
        model.setFormat("4444");
        Assert.assertTrue(model.read());
        Assert.assertEquals("321", model.getFormat());

        Assert.assertTrue(model.delete());
        Assert.assertFalse(model.read());
    }

    @Test
    public void users() throws SQLException {
        UserModel model = new UserModel();
        //create
        model.setUsername("qwerty123");
        model.setPassword("123123");
        model.create();

        //read
        model.setPassword("123123123");
        Assert.assertTrue(model.read());
        Assert.assertEquals("123123", model.getPassword());

        //update
        model.setPassword("123123123");
        Assert.assertTrue(model.update());
        Assert.assertTrue(model.read());
        Assert.assertEquals("123123123", model.getPassword());

        //delete
        Assert.assertTrue(model.delete());
        Assert.assertFalse(model.read());
    }

    @Test
    public void conf() {
        PasswordHandler ph = Config.getInstance().getPasswordHandler();
        String ee = ph.hash("21312312");
    }

    @Test
    public void mp() throws SQLException {
        NtripCasterModel model = new NtripCasterModel();
        model.setId(1);
        ArrayList<Integer> i = model.readMountpointsId();
    }
}
