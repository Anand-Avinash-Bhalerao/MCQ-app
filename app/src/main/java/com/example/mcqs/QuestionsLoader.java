package com.example.mcqs;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class QuestionsLoader extends AsyncTaskLoader<List<QuestionsData>> {
    public final String LOG_TAG = QuestionsLoader.class.getName();
    public String ques_url = "";
    public Context contextC;
    public String result = "";
    public List<List<QuestionsData>> dono;
    public List<QuestionsData> questionsData100 = new ArrayList<>();
    public List<QuestionsData> questionsData200 = new ArrayList<>();
    public HashMap<String,String> map = new HashMap<>();
    public int no_of_100,no_of_200;
    public QuestionsLoader(@NonNull Context context,String type) {
        super(context);
        map.put("MATHS","https://api.jsonbin.io/b/6192379762ed886f914e8117/4");
        map.put("STUPID","https://api.jsonbin.io/b/618a8121763da443125dfced/2");
        map.put("ANIME","https://api.jsonbin.io/b/6193ddd062ed886f914f47f7/1");
        String current= map.get(type);
        Log.d("CurrentURL", "for the type "+type+",the url is "+current);
        ques_url = current;
        this.contextC = context;
    }

    @Nullable
    @Override
    public List<QuestionsData> loadInBackground() {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        URL url = createUrl(ques_url);
        try {
            assert url != null;
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.d(LOG_TAG, "after OpenConnection");
            urlConnection.setRequestMethod("GET");
            Log.d(LOG_TAG, "after setReq");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            Log.d(LOG_TAG, "after readTimeout");
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            Log.d(LOG_TAG, "after connectTime");
            urlConnection.connect();
            Log.d(LOG_TAG, "after connect()");
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.d(LOG_TAG, "The connection was made");
            } else {
                Log.d(LOG_TAG, "The connection was not made");
            }
            result = jsonResponse;
            try {
                Log.d("bekar", "" + result);
                JSONObject baseJsonResponse = new JSONObject(result);
                Log.d(LOG_TAG, "Base length is: " + baseJsonResponse.length());
//                int noOfQues = baseJsonResponse.optInt("noOfQuestions");
                no_of_100 = Integer.parseInt(baseJsonResponse.getString("no_of_100"));
                no_of_200 = Integer.parseInt(baseJsonResponse.getString("no_of_200"));
                JSONArray array = baseJsonResponse.getJSONArray("questions100");
                JSONArray array200 = baseJsonResponse.getJSONArray("questions200");
                Log.d(LOG_TAG, "The size of the 200 wale array is " + array200.length());
                Log.d(LOG_TAG, "The size of the array is " + array.length());
                for (int i = 0; i < array.length(); i++) {
                    QuestionsData temporary = new QuestionsData();
                    JSONObject temp = array.getJSONObject(i);
                    temporary.setQuestion(temp.getString("question"));
                    temporary.setOption1(temp.getString("option1"));
                    temporary.setOption2(temp.getString("option2"));
                    temporary.setOption3(temp.getString("option3"));
                    temporary.setCorrect(temp.getString("correct"));
                    String qB = temp.optString("question_img_present");
                    String oB = temp.optString("option_img_present");
                    if(!qB.equals("")){
                        temporary.setQues_img_present(Integer.parseInt(qB));
                    }
                    else{
                        temporary.setQues_img_present(0);
                    }
                    if(!oB.equals("")){
                        temporary.setOptions_img_present(Integer.parseInt(oB));
                    }
                    else{
                        temporary.setQues_img_present(0);
                    }
                    temporary.setQuestions_img(temp.optString("question_img"));
                    temporary.setScore(Integer.parseInt(temp.optString("points")));
                    temporary.setTime(Integer.parseInt(temp.optString("time")));
                    temporary.setSolved(false);
                    temporary.setSolvedText(temporary.NOT_SOLVED);
                    questionsData100.add(temporary);
                }
//                Log.d(LOG_TAG,"dono ka size is "+dono.size());
//                questionsData = new ArrayList<>();
                Log.d(LOG_TAG, "array200 size " + array200.length());
                for (int i = 0; i < array200.length(); i++) {
                    Log.d(LOG_TAG,"yaha toh aa gaya");
                    QuestionsData temporary = new QuestionsData();
                    JSONObject temp = array200.getJSONObject(i);
                    temporary.setQuestion(temp.getString("question"));
                    temporary.setOption1(temp.getString("option1"));
                    temporary.setOption2(temp.getString("option2"));
                    temporary.setOption3(temp.getString("option3"));
                    temporary.setCorrect(temp.getString("correct"));
                    String qB = temp.optString("question_img_present");
                    String oB = temp.optString("option_img_present");
                    if(!qB.equals("")){
                        temporary.setQues_img_present(Integer.parseInt(qB));
                    }
                    else{
                        temporary.setQues_img_present(0);
                    }
                    if(!oB.equals("")){
                        temporary.setOptions_img_present(Integer.parseInt(oB));
                    }
                    else{
                        temporary.setQues_img_present(0);
                    }
                    temporary.setQuestions_img(temp.optString("question_img"));
                    temporary.setScore(Integer.parseInt(temp.optString("points")));
                    temporary.setTime(Integer.parseInt(temp.optString("time")));
                    temporary.setSolved(false);
                    temporary.setSolvedText(temporary.NOT_SOLVED);
                    questionsData200.add(temporary);
                }
                Log.d(LOG_TAG,"The size of dono array is "+dono.size());
                Log.d(LOG_TAG,"The size of dono array0 is "+dono.get(0).size());
                Log.d(LOG_TAG,"The size of dono array1 is "+dono.get(1).size());
            }catch (Exception e){
                Log.d(LOG_TAG, "Fetch karne me shayad kuch problem hai "+e);
            }
        } catch (IOException e) {
        } catch (Exception e) {
            Log.e(LOG_TAG, "kuch toh hua");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.shuffle(questionsData100);
        Collections.shuffle(questionsData200);
        List<QuestionsData> toSend = new ArrayList<>();
        Log.d("QUESTIONS_FROM_NET","100 wala");
        for(int i =0;i<no_of_100;i++){
            toSend.add(questionsData100.get(i));
//            Log.d("QUESTIONS_FROM_NET",questionsData100.get(i).getQuestion());
        }
        Log.d("QUESTIONS_FROM_NET","Ab 200 wala");
        for(int i =0;i<no_of_200;i++){

            toSend.add(questionsData200.get(i));
//            Log.d("QUESTIONS_FROM_NET",questionsData200.get(i).getQuestion());
        }

//        toSend.addAll(questionsData100);
//        toSend.addAll(questionsData200);
//        for(int i = 0;i<2;i++){
//            toSend.add(questionsData100.get(i));
//            toSend.add(questionsData200.get(i));
//        }
        for(int i =0;i<toSend.size();i++)
            Log.d("TheNEW",toSend.get(i).toString());
        Collections.shuffle(toSend);
        return toSend;
    }

    @NonNull
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;

        }
        return url;
    }
}
