package com.example.zjm.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private TextView mQuestionTextView;
    private static final String TAG="MainActivity";
    private static final String KEY_INDEX="index";
    private Button mCheatButton;//cheat按钮
    private boolean mIsCheater;
    private static final String KEY_INDEX_CHEAT="index";
    private TextView SDK_VERSION;


    //增加TrueFalse对象数组
    private TrueFalse[] mQuestionBank=new TrueFalse[]{
            new TrueFalse(R.string.question_oceans,true),
            new TrueFalse(R.string.question_mideast,true),
            new TrueFalse(R.string.question_africa,false),
            new TrueFalse(R.string.question_americas,false),
            new TrueFalse(R.string.question_asia,true),
    };
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (data==null){
            return;
        }
        mIsCheater=data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOW,false);
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart()");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPaust");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"保存实例");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        int mIsCheater_int=(mIsCheater)?1:0;//boolean转化为int
        savedInstanceState.putInt(KEY_INDEX_CHEAT,mIsCheater_int);
    }

    private int mCurrentIndex=0;//初始化指定数组
    private void updataQuestion(){//将更新的公共代码封装
        int question=mQuestionBank[mCurrentIndex].getmQuestion();
        mQuestionTextView.setText(question);//将问题显示出来
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId=0;//
        if (mIsCheater){//若偷看了答案提醒Cheat is wrong
            //这里不是很完善的一点就是，偷看了一题的答案会被认为偷看全部答案
            messageResId=R.string.judgment_toast;
        }else {
            if (userPressedTrue==answerIsTrue){
                messageResId=R.string.correct_toast;//如果正确
            }else{
                messageResId=R.string.incorrect_toast;//如果不正确
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SDK_VERSION= (TextView) findViewById(R.id.SDK_VERSITON);
        SDK_VERSION.setText(Build.VERSION.SDK_INT);


        mTrueButton= (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //响应TRUEA按钮的事件
                checkAnswer(true);
            }
        });
        //
        mFalseButton= (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //响应False按钮的事件
                checkAnswer(false);
            }
        });
        //
        mQuestionTextView= (TextView) findViewById(R.id.question_text_view);
        mNextButton= (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //响应Next按钮事件
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;//每次调用增加1
                updataQuestion();//更新问题
            }
        });
        mBackButton=(ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //响应Back按钮
                mCurrentIndex=(mCurrentIndex-1)%mQuestionBank.length;//每次调用减少1
                updataQuestion();//更新问题
            }
        });
        if (savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);//如果不为空，则将mCurrentIndex键值对赋值给KEY_INDEX
            mIsCheater=savedInstanceState.getBoolean(KEY_INDEX_CHEAT,false);
        }
        updataQuestion();//更新问题
        mCheatButton= (Button) findViewById(R.id.check_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Cheat按钮处理逻辑
                //Start cheatActivity
                Intent i=new Intent(MainActivity.this,CheatActivity.class);
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,answerIsTrue);
                startActivityForResult(i,0);
            }
        });

    }
}
