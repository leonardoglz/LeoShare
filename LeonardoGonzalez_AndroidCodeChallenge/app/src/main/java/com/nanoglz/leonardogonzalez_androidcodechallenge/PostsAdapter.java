package com.nanoglz.leonardogonzalez_androidcodechallenge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by m11 on 11/16/2015.
 */
public class PostsAdapter extends ArrayAdapter<Post> {
    Context context;

    public PostsAdapter(Context context, List<Post> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        View view = LayoutInflater.from(context).inflate(R.layout.post, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Log.i("Test","thumbnail=" + post.thumbnail);
        if (post.thumbnail != null) {
            //Picasso takes care of the background task to load image (no need to use AsyncTask)
            Picasso.with(context).load(post.thumbnail)
                    .resize(200, 200)
                    .centerCrop()
                    .into(imageView);
        }

        TextView textViewAuthor = (TextView) view.findViewById(R.id.textViewAuthor);
        textViewAuthor.setText(post.author);
        TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        //if the title is to big, I will show only the first 80 chars
        StringBuilder title = new StringBuilder(post.title);
        if (title.length() > 80) {
            title = title.delete(77, title.length()-1).append("...");
        }
        textViewTitle.setText(title);

        TextView textViewComments = (TextView) view.findViewById(R.id.textViewComments);
        textViewComments.setText(post.num_comments + " " + context.getString(R.string.comments));
        TextView textViewUps = (TextView) view.findViewById(R.id.textViewUps);
        textViewUps.setText(post.ups + " " + context.getString(R.string.ups));
        TextView textViewDowns = (TextView) view.findViewById(R.id.textViewDowns);
        textViewDowns.setText(post.downs + " " + context.getString(R.string.downs));

        return view ;
    }
}
