package id.co.technomotion.websocketsimple.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import id.co.technomotion.websocketsimple.Application.ChatApplication;
import id.co.technomotion.websocketsimple.R;
import id.co.technomotion.websocketsimple.model.Comment;
import id.co.technomotion.websocketsimple.model.event.OnCommentNew;
import id.co.technomotion.websocketsimple.model.event.OnPostCommentSucceeded;
import id.co.technomotion.websocketsimple.model.event.OnTypingStart;
import id.co.technomotion.websocketsimple.model.event.OnTypingStop;
import id.co.technomotion.websocketsimple.model.event.OnUserJoined;
import id.co.technomotion.websocketsimple.model.event.OnUserLeft;
import id.co.technomotion.websocketsimple.ui.adapter.CommentAdapter;

;

public class MainActivity extends ActionBarActivity {

    private EditText  commentBox;
    private Button btnSend;
    private ListView listViewChat;
    private CommentAdapter adapter;
    private ArrayList<Comment> listComment;
    private ChatApplication app;
    private ActionBar actionBar;

    private Date lastTypeDate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app= (ChatApplication) getApplication();

        EventBus.getDefault().register(this);

        commentBox= (EditText) findViewById(R.id.input);
        btnSend= (Button) findViewById(R.id.buttonSend);
        listViewChat= (ListView) findViewById(R.id.listViewChat);

        actionBar=getSupportActionBar();
        actionBar.setTitle("ChatOK!");
        actionBar.setSubtitle("the realtime chat");
        /**
         * init adapter
         */
        listComment=new ArrayList<>();
        adapter=new CommentAdapter(this,R.layout.item_comment,listComment);
        listViewChat.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentBox.getText().toString().isEmpty())
                    return;
                app.getChat().postComment(commentBox.getText().toString());
            }

        });

        commentBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                lastTypeDate=new Date();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("typing...");

                app.getChat().typing();

                Timer timer=new Timer();
                final TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {
                        Date runtimeDate=new Date();
                        if((lastTypeDate.getTime()+1000)<=runtimeDate.getTime()){
                            System.out.println("stop typing...");
                            app.getChat().typingFinished();
                        }
                    }
                };
                timer.schedule(timerTask,1000);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEvent(final OnUserJoined e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listComment.add(e.comment);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void onEvent(final OnUserLeft e){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listComment.add(e.comment);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void onEvent(OnTypingStart e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                actionBar.setSubtitle("someone is typing...");
            }
        });
    }

    public void onEvent(final OnTypingStop e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                actionBar.setSubtitle("the realtime chat");
            }
        });
    }
    public void onEvent(final OnCommentNew e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("main onCommentNEw "+e.comment.toString());
                listComment.add(e.comment);
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void onEvent(final OnPostCommentSucceeded e){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("main onCommentNEw "+e.comment.toString());
                listComment.add(e.comment);
                adapter.notifyDataSetChanged();
                commentBox.setText("");
            }
        });
    }
}
