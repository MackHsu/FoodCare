package com.example.foodcare.ToolClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class SaveFile {

    public static boolean save(Context context, String name, String pass,String land,int id) {
        try {
            //File f=new File("/data/data/com/csdn/www/info.txt");
            File f = new File(context.getFilesDir(), "info.txt");
            //context.getFilesDir();//返回一个目录  /data/data/包名/files
            FileOutputStream fos = new FileOutputStream(f);
            /**这里呢，使用land标识登录状态，其中yes代表登陆，no代表没，注意全是小写n*/
            fos.write((name + "==" + pass+"is"+land+"key"+id).getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取保存的数据
     *
     * @param context
     * @return
     */
    public static Map<String, String> getSaveFiles(Context context) {

        File f = new File(context.getFilesDir(), "info.txt");
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String str = br.readLine();
            String[] infos = str.split("==");
            String[] _land=infos[1].split("is");
            String[] _key=_land[1].split("key");
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", infos[0]);
            map.put("userpass", _land[0]);
            map.put("landing_status",_key[0]);
            map.put("id",_key[1]);


            return map;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
