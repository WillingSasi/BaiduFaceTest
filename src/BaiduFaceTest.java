import com.baidu.aip.face.AipFace;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BaiduFaceTest {

    //设置appid/ak/sk
    public static final String APP_ID = "10033517";
    public static final String API_KEY = "VC6RlsGrUxqfMfwlLL8GlZQb";
    public static final String SECERT_KEY = "kK0khM5rQ7ZITDpX1NtRubWSCw6Gazjr";

    public static void main(String[] args) {
        String imagePath = "D:\\IdeaProjects\\BaiduFaceTest\\stats\\IMG_20170803_083347_HDR.jpg";
        BaiduFaceTest faceTest = new BaiduFaceTest();
        faceTest.faceCheck(imagePath);
//        faceTest.faceMatch();
    }

    //人脸检测
    public void faceCheck(String imagePath){
        AipFace aipFaceClient = new AipFace(APP_ID,API_KEY,SECERT_KEY);

        aipFaceClient.setConnectionTimeoutInMillis(2000);
        aipFaceClient.setSocketTimeoutInMillis(60000);

        HashMap<String,String> hashMap = new HashMap<String,String>();
//        hashMap.put("face_fields","age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");
        hashMap.put("face_fields","age,beauty,glasses");
        hashMap.put("max_face_num","20");
        JSONObject response = aipFaceClient.detect(imagePath,hashMap);
//        System.out.println(response);

        JSONArray response1 = (JSONArray) response.get("result");
        for (int i = 0;i < response1.length();i++){
            JSONObject response2 = (JSONObject) response1.get(i);
            JSONObject response3 = (JSONObject) response2.get("location");
            int y = (int)response3.get("top");
            int x = (int)response3.get("left");
            int w = (int)response3.get("width");
            int h = (int)response3.get("height");
            double beauty = (double) response2.get("beauty");
            double age = (double) response2.get("age");

            try {
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imagePath));
                Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
                graphics2D.setColor(Color.RED);
                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.drawRect(x,y,w,h);
                ImageIO.write(bufferedImage,"JPG",new FileOutputStream(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("颜值度："+beauty);
            System.out.println("年龄："+age);
        }
    }

    //人脸相似度
    public void faceMatch(){
        String image1 = "D:\\IdeaProjects\\BaiduFaceTest\\stats\\IMG_20170811_192837_HDR.jpg";
        String image2 = "D:\\IdeaProjects\\BaiduFaceTest\\stats\\IMG_20170811_192832_HDR.jpg";
        AipFace aipFaceClient = new AipFace(APP_ID,API_KEY,SECERT_KEY);

        aipFaceClient.setConnectionTimeoutInMillis(2000);
        aipFaceClient.setSocketTimeoutInMillis(60000);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(image1);
        arrayList.add(image2);

        JSONObject response = aipFaceClient.match(arrayList,new HashMap<String ,String>());
        System.out.println(response);

    }
}
