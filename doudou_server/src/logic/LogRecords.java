package logic;

import java.io.*;
import java.util.Date;

public class LogRecords {
    /*	private static	File file=new file();*/
    public static void Log(MyUser user, String record) {
        System.out.println(record);
        java.io.File file = new java.io.File("log/logtext.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        OutputStreamWriter write = null;
        try {
            write = new OutputStreamWriter(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedWriter writer = new BufferedWriter(write);

        try {
            Date date = new Date(System.currentTimeMillis());

            writer.write(date + record);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
