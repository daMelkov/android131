package com.astra.melkovhw131;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private static final String FILE_NAME = "data.txt";

    /**
     * Check pair "login+password" exists
     * @param context context
     * @param login login
     * @param password password
     * @return true: account exists, false: account not exists
     */
    public static boolean checkExists(Context context, String login, String password) {
        Map<String, String> items = getAccounts(context);
        for(String item : items.keySet()) {
            if(login.equals(item) && password.equals(items.get(item))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check login exists (pre-registration test)
     * @param context context
     * @param login login
     * @return true: login exists, false: login not exists
     */
    public static boolean checkExists(Context context, String login) {
        Map<String, String> items = getAccounts(context);

        for (String item : items.keySet()) {
            if (item.equals(login)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Write content to file in private external storage
     *
     * @param context context (Activity)
     * @param login login
     * @param password password
     */
    public static void addData(Context context, String login, String password) {
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(login + ";" + password + "\n");
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

    private static Map<String, String> getAccounts(Context context) {
        Map<String, String> result = new HashMap<>();

        FileReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            if(!file.exists()) {
                file.createNewFile();
            }

            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            while (line != null) {
                // split string on login (0) and password (1)denis
                String[] data = line.split(";");

                // add one account (login+password)
                result.put(data[0], data[1]);

                // next denisline in format "login;format"
                line = bufferedReader.readLine();
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

        return result;
    }
}