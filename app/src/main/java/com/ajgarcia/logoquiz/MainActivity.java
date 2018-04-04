package com.ajgarcia.logoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ajgarcia.logoquiz.Adapter.GridViewAnswerAdapter;
import com.ajgarcia.logoquiz.Adapter.GridViewSuggestAdapter;
import com.ajgarcia.logoquiz.Common.Common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;

    public Button btnSubmit;

    public GridView gridViewAnswer,gridViewSuggest;

    public ImageView imageViewQuestion;

    int[] image_list={
            R.drawable.batman,
            R.drawable.captainamerica,
            R.drawable.deadpool,
            R.drawable.facebook,
            R.drawable.instagram,
            R.drawable.skype,
            R.drawable.spiderman,
            R.drawable.superman,
            R.drawable.twitter,
            R.drawable.youtube,

    };

    public char[] answer;

    String correct_answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        gridViewAnswer = (GridView)findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView)findViewById(R.id.gridViewSuggest);

        imageViewQuestion = (ImageView)findViewById(R.id.imgLogo);

        //add setuplist here
        setupList();

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result="";
                for (int i=0;i< Common.user_submit_answer.length;i++)
                    result+=String.valueOf(Common.user_submit_answer[i]);
                if(result.equals(correct_answer))
                {
                    Toast.makeText(getApplicationContext(),"Correct! This is"+result,Toast.LENGTH_SHORT).show();

                    //reset
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    //set adapater
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource,getApplicationContext(),MainActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Incorrect!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        //setup suggest character list and image question...random logo
        Random random = new Random();
        int imageSelected = image_list[random.nextInt(image_list.length)];
        imageViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

        answer = correct_answer.toCharArray();

        Common.user_submit_answer = new char[answer.length];

        // Add answer character to list
        suggestSource.clear();
        for (char item:answer)
        {
            //add logo name to list
            suggestSource.add(String.valueOf(item));
        }
        //random add some character to list
        for (int i = answer.length;i<answer.length*2;i++)
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

        //sort  random
        Collections.shuffle(suggestSource);

        //set for gridview
        answerAdapter = new GridViewAnswerAdapter(setupNullList(),this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource,this,this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);


    }

    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for(int i=0;i<answer.length;i++)
            result[i]=' ';
        return result;
    }
}
