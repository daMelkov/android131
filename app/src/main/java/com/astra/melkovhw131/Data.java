package com.astra.melkovhw131;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Data {
    private static final String FILE_NAME = "data.txt";

    public static boolean checkExists(Context context, String login, String password) {
        boolean result = false;

        if (isExternalStorageReadable()) {
            FileReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                // read file line by line
                File file = new File(context.getExternalFilesDir(null), FILE_NAME);
                reader = new FileReader(file);
                bufferedReader = new BufferedReader(reader);

                String line = bufferedReader.readLine();
                while (line != null) {
                    // line in format "login;format"
                    line = bufferedReader.readLine();

                    // split string on login (0) and password (1)
                    String[] data = line.split(";");

                    // check: login and password exists
                    if(data[0].equals(login) && data[1].equals(password)) {
                        result = true;
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * Write content to file in private external storage
     *
     * @param context context (Activity)
     * @param login login
     * @param password password
     */
    public static void addData(Context context, String login, String password, boolean create) {
        if (isExternalStorageWritable()) {
            File file = new File(context.getExternalFilesDir(null), FILE_NAME);

            // init data file with login and password - only one time
            // create-mode doesn't write login/password (return)
            if(create) {
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return;
            }

            FileWriter writer = null;
            try {
                writer = new FileWriter(file, true);
                writer.write(login + ";" + password);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks if external storage is available for read and write
     * @return flag: storage ready to write
     */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if external storage is available to at least read
     * @return flag: storage ready to read
     */
    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}