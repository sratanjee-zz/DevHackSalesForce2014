package com.shelfy;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.parse.ParseObject;

/**
 * Created by Sarang on 10/10/14.
 */
public class NewMessagesFragment extends Fragment {

    RelativeLayout moodView;
    ImageView voteDownView;
    ImageView voteUpView;
    SharedPreferences prefs;

    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.new_messages_fragment, container, false);

        prefs = getActivity().getSharedPreferences("APP_PREFS",
                Context.MODE_PRIVATE);

        voteDownView = (ImageView) rootView.findViewById (R.id.vote_down_view);
        voteUpView = (ImageView) rootView.findViewById (R.id.vote_up_view);

        
        voteDownView.setImageResource(R.drawable.vote_down_button);
        voteUpView.setImageResource(R.drawable.vote_up_button);

                //setData(message);
                voteUpView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseObject testObject = new ParseObject("Shelfy");
                        testObject.put("foo", "bar");
                        testObject.saveInBackground();
                    }
                });

                voteDownView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseObject testObject = new ParseObject("Shelfy");
                        testObject.put("foo", "bar");
                        testObject.saveInBackground();
                    }
                });


        return rootView;
    }

    public Response.Listener<JSONObject> postVoteResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            response.toString().replaceAll("\\\\/", "/");
            ((NewMessagesActivity) getActivity()).updateMessages();
        }
    };
}