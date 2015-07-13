package id.co.technomotion.websocketsimple.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.co.technomotion.websocketsimple.R;
import id.co.technomotion.websocketsimple.model.Comment;

/**
 * Created by omayib on 7/11/15.
 */
public class CommentAdapter extends ArrayAdapter<Comment>{

    private int resource;
    private LayoutInflater inflater;

    public CommentAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        this.resource=resource;
        this.inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment currentComment=getItem(position);
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView=inflater.inflate(resource,parent,false);

            holder.username= (TextView) convertView.findViewById(R.id.tv_username);
            holder.message= (TextView) convertView.findViewById(R.id.tv_message);

            convertView.setTag(holder);

        }else{
            holder= (Holder) convertView.getTag();
        }

        holder.username.setText(currentComment.username);
        holder.message.setText(currentComment.message);

        return convertView;
    }

    private static class Holder{
        TextView username,message;
    }
}
