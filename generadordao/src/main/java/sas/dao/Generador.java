package sas.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generador {

    public static void main(String args[]) throws Exception {

        String packageName = "com.yozzibeens.rivostaxipartner";
        String dbName = "RivosPartner";

        PlantillaGenerador oPlantillaGenerador = new PlantillaGenerador(dbName, packageName, args[0]);
        Schema schema = new Schema(1, packageName + ".modelo");

        Entity usuario = schema.addEntity("Cabbie");
        usuario.addIdProperty().primaryKey().autoincrement();
        usuario.addStringProperty("Cabbie_Id");
        usuario.addStringProperty("Name");
        usuario.addStringProperty("Email");
        usuario.addStringProperty("Phone");
        usuario.addStringProperty("Image");
        oPlantillaGenerador.generarController("Cabbie", false);

        new DaoGenerator().generateAll(schema, args[0]);
        oPlantillaGenerador.generarDB();
    }
}
