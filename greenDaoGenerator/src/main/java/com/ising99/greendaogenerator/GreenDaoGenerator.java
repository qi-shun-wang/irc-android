package com.ising99.greendaogenerator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.ising99.intelligentremotecontrol.db"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addDeviceEntities(schema);
        // addPhonesEntities(schema);
    }

    // This is use to describe the colums of your table
    private static void addDeviceEntities(final Schema schema) {
        Entity device = schema.addEntity("Device");
        device.addStringProperty("address").primaryKey();
        device.addStringProperty("name").notNull();
        device.addStringProperty("settings");
        device.addDateProperty("update_at").notNull();
        device.addDateProperty("create_at").notNull();
    }
}
