package com.example.kingominho;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebViewFragment.OnWebViewFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {
    private static final String ARG_PARAM1 = "url";
    //private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    //private String mParam2;

    private OnWebViewFragmentInteractionListener mListener;

    private WebView webView;

    public WebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment WebViewFragment.
     */
    public static WebViewFragment newInstance(String param1){ //String param2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.webViewLabel));

        webView = (WebView) getView().findViewById(R.id.webView);


        final ShimmerFrameLayout container = (ShimmerFrameLayout) getView().findViewById(R.id.shimmer_view_container);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                container.startShimmer();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                container.stopShimmer();
                webView.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (URLUtil.isNetworkUrl(url)) {
                    return false;
                } else {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Appropriate Application not installed.", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //return super.shouldOverrideUrlLoading(view, request);

                if (URLUtil.isNetworkUrl(request.getUrl().toString())) {
                    return false;
                } else {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(request.getUrl().toString()));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //Toast.makeText(getActivity(), request.getUrl().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Appropriate Application not installed.", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        //webSettings.setLoadWithOverviewMode(true);
        //webSettings.setUseWideViewPort(true);
        //String url = getActivity().getResources().getString(R.string.blogLink);
        if (mParam1 != null) {
            String url = mParam1;
            webView.loadUrl(url);
        }
    }

    //mListener.onWebViewFragmentInteraction(webView);

    void webNavigation() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            mListener.onWebViewFragmentBackButtonPressedFromHomeWebsite();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWebViewFragmentInteractionListener) {
            mListener = (OnWebViewFragmentInteractionListener) context;
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
    public interface OnWebViewFragmentInteractionListener {
        void onWebViewFragmentBackButtonPressedFromHomeWebsite();
    }
}
