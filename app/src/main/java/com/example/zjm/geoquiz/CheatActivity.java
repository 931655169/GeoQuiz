package com.example.zjm.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zjm on 2016/10/21.
 */

public class CheatActivity extends Activity {
    public static final String EXTRA_ANSWER_IS_TRUE=
            "com.bignerdranch.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOW=
            "com.bignerdranch.android.geoquiz.answer_shown";
    private void setAnswerShowResult(boolean isAnswerShown){//设置答案显示结果
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW,isAnswerShown);
        setResult(RESULT_OK,data);
    }

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);//得到Mainactivity传递的值，如无法取得默认值为false
        mAnswerTextView=(TextView)findViewById(R.id.answerTextView);

        mShowAnswer= (Button) findViewById(R.id.showAnswerButton);
        setAnswerShowResult(false);
        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //显示答案按钮
                if (mAnswerIsTrue){//如果答案为真
                    mAnswerTextView.setText(R.string.true_button);
                }else {//如果答案为假
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShowResult(true);
            }
        });
    }
}
