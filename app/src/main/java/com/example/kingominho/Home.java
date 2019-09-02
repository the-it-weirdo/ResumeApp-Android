package com.example.kingominho;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnHomeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    /*
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;*/

    static final String emailKey = "Mail.EMAIL";
    static final String subjectKey = "Mail.SUBJECT";

    private OnHomeFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.homeLabel));

        TextView facebook = (TextView)getView().findViewById(R.id.textView9);
        TextView linkedin = (TextView)getView().findViewById(R.id.textView10);
        TextView hackerrank = (TextView)getView().findViewById(R.id.textView11);
        TextView github = (TextView)getView().findViewById(R.id.textView13);


        facebook.setMovementMethod(LinkMovementMethod.getInstance());
        linkedin.setMovementMethod(LinkMovementMethod.getInstance());
        hackerrank.setMovementMethod(LinkMovementMethod.getInstance());
        github.setMovementMethod(LinkMovementMethod.getInstance());

        FloatingActionButton fabMail = getView().findViewById(R.id.fabMail);
        fabMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getResources().getString(R.string.myEmail);
                String subject = getResources().getString(R.string.mailSubjectString);
                Bundle bundle = new Bundle();
                bundle.putString(emailKey, email);
                bundle.putString(subjectKey, subject);
                onSendMailButtonPressed(bundle);
                /*Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                //i.setDataAndType(Uri.parse("email"), "message/rfc822");
                //i.setData(Uri.parse("email"));
                //i.setType("message/rfc822"); //Type for email
                //i.putExtra(Intent.EXTRA_EMAIL, email);
                intent.putExtra(Intent.EXTRA_SUBJECT, ));*/
            }
        });

        FloatingActionButton fabCall = getView().findViewById(R.id.fabCall);
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //callAction();
                onCallButtonPressed(Uri.parse("tel:7478755667"));
            }
        });
    }

    private void onCallButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCallButtonPressed(uri);
        }
    }

    private void onSendMailButtonPressed(Bundle bundle)
    {
        if(mListener != null)
        {
            mListener.onMailButtonPressed(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCallButtonPressed(Uri uri);
        void onMailButtonPressed(Bundle bundle);
    }
}
