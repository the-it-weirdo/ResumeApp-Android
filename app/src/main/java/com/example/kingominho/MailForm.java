package com.example.kingominho;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MailForm.OnMailFormFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MailForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MailForm extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    static final String emailKey = "Mail.EMAIL";
    static final String subjectKey = "Mail.SUBJECT";
    static final String bodyKey = "Mail.BODY";


    //private String mParam1;
    //private String mParam2;

    private OnMailFormFragmentInteractionListener mListener;

    public MailForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MailForm.
     */
    // TODO: Rename and change types and number of parameters
    public static MailForm newInstance() {
        MailForm fragment = new MailForm();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mail_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.mailFormLabel));

        final EditText subjectField = (EditText) getView().findViewById(R.id.subjectText);
        final EditText bodyField = (EditText) getView().findViewById(R.id.bodyText);
        Button sendMailButton = (Button) getView().findViewById(R.id.sendMailButton);

        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = getResources().getString(R.string.myEmail);
                final String subject = subjectField.getText().toString();
                final String body = bodyField.getText().toString();

                if (subject.trim().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.emptySubjectDialogMessage).setTitle(R.string.emptySubjectDialogTitle);
                    builder.setCancelable(true);
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    subjectField.requestFocus();
                } else if (body.trim().isEmpty()) {
                    bodyField.requestFocus();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.emptyBodyDialogMessage).setTitle(R.string.emptyBodyDialogTitle);
                    builder.setCancelable(true);
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Bundle bundle = makeMailBundle(email, subject, body);
                            onSendMailButtonPressed(bundle);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Bundle bundle = makeMailBundle(email, subject, body);
                    onSendMailButtonPressed(bundle);
                }
            }
        });
    }

    private Bundle makeMailBundle(String email, String subject, String body) {
        Bundle bundle = new Bundle();
        bundle.putString(emailKey, email);
        bundle.putString(subjectKey, subject);
        bundle.putString(bodyKey, body);
        return bundle;
    }


    private void onSendMailButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onSendMailButtonPressed(bundle);
        }
    }

    void onBackButtonPressed()
    {
        if(mListener != null)
        {
            mListener.onBackPressedFromMailForm();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMailFormFragmentInteractionListener) {
            mListener = (OnMailFormFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnMailFormFragmentInteractionListener {

        void onSendMailButtonPressed(Bundle bundle);
        void onBackPressedFromMailForm();
    }
}
