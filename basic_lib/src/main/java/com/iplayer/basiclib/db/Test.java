package com.iplayer.basiclib.db;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Administrator on 2017/9/19.
 * 数据库测试类
 */

public class Test {
    public static void main(String[] arg) {
        try {
            Schema schema = new Schema(1, "com.iplayer.basiclib.db.dao");
            addNote(schema);
            new DaoGenerator().generateAll(schema, "./basic_lib/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty().primaryKey().autoincrement();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }
}
