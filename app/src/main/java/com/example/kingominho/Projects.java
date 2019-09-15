package com.example.kingominho;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Projects.OnProjectsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Projects #newInstance} factory method to
 * create an instance of this fragment.
 */
public class Projects extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    private ArrayList<ProjectListItem> mProjectList;
    private RecyclerView mRecyclerView;
    private ProjectListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    //private String mParam1;
    //private String mParam2;

    private OnProjectsFragmentInteractionListener mListener;

    public Projects() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Projects.
     */
    // TODO: Rename and change types and number of parameters
    public static Projects newInstance(String param1, String param2) {
        Projects fragment = new Projects();
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
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.projectRecycler);

        makeList();
        buildRecyclerView();
    }

    public void makeList()
    {
        final String[] titleList = getResources().getStringArray(R.array.projectsTitleArray);
        final String[] durationList = getResources().getStringArray(R.array.projectsDurationArray);
        final String[] linkList = getResources().getStringArray(R.array.projectsLinkArray);

        if(titleList.length != durationList.length || durationList.length != linkList.length )
        {
            throw new RuntimeException("titleList.length, durationList.length, linkList.length must be equal!!");
        }
        else {
            mProjectList = new ArrayList<>();

            for (int i = 0; i < titleList.length; i++) {
                mProjectList.add(new ProjectListItem(titleList[i], durationList[i], linkList[i]));
            }
        }
    }

    public void buildRecyclerView()
    {
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ProjectListAdapter(mProjectList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemCLickListener(new ProjectListAdapter.OnItemClickListener() {
            @Override
            public void onGitButtonPressed(int position) {
                String link = mProjectList.get(position).getLink();
                Uri uri = Uri.parse(link);
                mListener.onGitButtonPressed(uri);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProjectsFragmentInteractionListener) {
            mListener = (OnProjectsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProjectsFragmentInteractionListener");
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

    public interface OnProjectsFragmentInteractionListener {
        void onGitButtonPressed(Uri uri);
    }
}
