package com.example.stonewang.gaodi.util;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.db.GuojiNews;
import com.example.stonewang.gaodi.db.JunshiNews;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by stoneWang on 2017/3/4.
 * 1.开始解析对象
 * 2.开始解析字符串
 * 3.获取到一串数组的json对象
 * 4.并存入数据库
 */

public class JsonUtil{

    /**
     * 将API返回的JSON数据中的Unicode编码的汉字，转码为utf-8格式的汉字
     * @param unicodeStr
     * @return
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    /**
     * 评论 京东云返回评论数据
     * @param jsonData
     */
    public static List<Comment> parseJsonComment(String jsonData){
        List<Comment> commentList =new ArrayList<>();
        if (!TextUtils.isEmpty(jsonData)){
            try{
                String jsonString = decode(jsonData);//转换编码

                JSONArray AllComment = new JSONArray(jsonString);
                for (int i=0; i<AllComment.length(); i++){
                    JSONObject CommentObject = AllComment.getJSONObject(i);

                    int commentId = CommentObject.getInt("id");
                    String news = CommentObject.getString("news");
                    int newsid = CommentObject.getInt("newsid");
                    String author = CommentObject.getString("author");
                    String content = CommentObject.getString("content");
                    String time = CommentObject.getString("time");

                    Comment comment = new Comment();

                    comment.setCommentId(commentId);
                    comment.setNews(news);
                    comment.setNewsid(newsid);
                    comment.setAuthor(author);
                    comment.setContent(content);
                    comment.setTime(time);

                    commentList.add(comment);//没用存入数据库，仅仅是保存在List里
                }
                return commentList;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 国际 京东云API返回的Guoji的JSON转码
     * @param jsonData
     * @return
     */
    public static boolean parseJsonGuoji(String jsonData){
        if (!TextUtils.isEmpty(jsonData)){
            try{
                String jsonString = decode(jsonData);

                JSONArray allNews = new JSONArray(jsonString);

                for (int i=0; i<allNews.length(); i++){

                    JSONObject NewsObject = allNews.getJSONObject(i);

                    int newsid = NewsObject.getInt("id");
                    String title = NewsObject.getString("title");
                    String date = NewsObject.getString("date");
                    String category = NewsObject.getString("category");
                    String author_name = NewsObject.getString("author_name");
                    String url = NewsObject.getString("url");
                    String thumbnail_pic_s = NewsObject.getString("thumbnail_pic_s");

                    GuojiNews News = new GuojiNews();

                    News.setNewsid(newsid);
                    News.setTitle(title);
                    News.setDate(date);
                    News.setCategory(category);
                    News.setAuthor_name(author_name);
                    News.setUrl(url);
                    News.setThumbnail_pic_s(thumbnail_pic_s);
                    News.save();
                }
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }



    /**
     * 军事 京东云API返回Junshi的JSON转码
     * @param jsonData
     * @return
     */
    public static boolean parseJsonJunshi(String jsonData){
        if (!TextUtils.isEmpty(jsonData)){
            try{
                String jsonString = decode(jsonData);
//                Log.d("APIone", "testAPI: "+jsonData);

                JSONArray allNews = new JSONArray(jsonString);
                for (int i=0; i<allNews.length(); i++){

                    JSONObject NewsObject = allNews.getJSONObject(i);

//                    int id = NewsObject.getInt("id");
                    int newsid = NewsObject.getInt("id");
                    String title = NewsObject.getString("title");
                    String date = NewsObject.getString("date");
                    String category = NewsObject.getString("category");
                    String author_name = NewsObject.getString("author_name");
                    String url = NewsObject.getString("url");
                    String thumbnail_pic_s = NewsObject.getString("thumbnail_pic_s");

                    JunshiNews News = new JunshiNews();

//                    News.setId(id);
                    News.setNewsid(newsid);
                    News.setTitle(title);
                    News.setDate(date);
                    News.setCategory(category);
                    News.setAuthor_name(author_name);
                    News.setUrl(url);
                    News.setThumbnail_pic_s(thumbnail_pic_s);
                    News.save();
//                    Log.d("APItwo", "testAPI: "+id+"======="
//                    +title+"======="
//                    +date+"======="
//                    +category+"======="
//                    +author_name+"======="
//                    +url+"======="
//                    +thumbnail_pic_s+"=======");
                }
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

}
