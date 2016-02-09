package de.kaubisch.movietheatre.view;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import de.kaubisch.movietheatre.R;

/**
 * Created by kaubisch on 07.02.16.
 */
public class ReviewViewHolder {

    public final TextView author;
    public final TextView content;

    public ReviewViewHolder(final View v) {
        author = (TextView) v.findViewById(R.id.review_author);
        content = (TextView) v.findViewById(R.id.review_content);
    }
}
