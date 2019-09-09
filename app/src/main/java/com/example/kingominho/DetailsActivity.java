package com.example.kingominho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity implements Home.OnHomeFragmentInteractionListener,
        Projects.OnProjectsFragmentInteractionListener, Skills.OnSkillsFragmentInteractionListener,
        Contact.OnContactFragmentInteractionListener, MailForm.OnMailFormFragmentInteractionListener,
        WebViewFragment.OnWebViewFragmentInteractionListener {

    private static final String webViewFragmentTag = "WEB_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String param = getIntent().getStringExtra("param");

        showFragment(param);
    }

    @Override
    public void onBackPressed() {
        WebViewFragment fragment = (WebViewFragment) getSupportFragmentManager().findFragmentByTag(webViewFragmentTag);
        if (fragment != null && fragment.isVisible()) {
            fragment.webNavigation();
        } else {
            super.onBackPressed();
        }
    }


    void showFragment(String param) {
        Fragment fragment = null;

        switch (param) {
            case "Home":
                fragment = new Home();
                break;
            case "Contact":
                fragment = new Contact();
                break;
            case "Skills":
                fragment = new Skills();
                break;
            case "Projects":
                fragment = new Projects();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Fragment not ready.", Toast.LENGTH_SHORT).show();
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private boolean isCallPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Call", "Permission is Granted!!");
                return true;
            } else {
                Log.v("Call", "Permission is Revoked!!");
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.permissionToastCall), Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else {
            Log.v("Call", "Permission is granted!!");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v("func", "Called");
        switch (requestCode) {
            case 1: //for Call
            {
                Toast.makeText(getApplicationContext(), "Case 1", Toast.LENGTH_SHORT).show();
                Log.v("Permission Result", "Case 1");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission is Granted!!", Toast.LENGTH_LONG).show();
                    //callAction();
                    onCallButtonPressed(Uri.parse("tel:7478755667"));
                } else {
                    Toast.makeText(getApplicationContext(), "Permission is Denied!!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default: {
                Log.v("onReqPermissionsRes", "default case reached. requestCode = " + requestCode);
            }
        }
    }


    //Home.OnHomeFragmentInteractionListener
    @Override //same for both Contact Fragment and Home fragment
    public void onCallButtonPressed(Uri uri) {
        if (isCallPermissionGranted()) {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(uri);
            startActivity(call);
        }
    }

    @Override
    public void onMailButtonPressed(Bundle bundle) {
        onSendMailButtonPressed(bundle);
    }

    //Projects.OnProjectsFragmentInteractionListener
    @Override
    public void onProjectsFragmentListViewInteraction(int i, String[] toastArray) {
        Toast.makeText(getApplicationContext(), String.valueOf(toastArray[i]), Toast.LENGTH_SHORT).show();
    }

    //Skills.OnSkillsFragmentInteractionListener
    @Override
    public void onSkillsFragmentGridViewInteraction(int i, String[] toastArray) {
        Toast.makeText(getApplicationContext(), toastArray[i], Toast.LENGTH_SHORT).show();
    }

    //Contact.OnContactFragmentInteractionListener
    @Override
    public void onMailButtonPressed() {
        MailForm fragment = new MailForm();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSmsButtonPressed(Bundle bundle) {
        //Toast.makeText(getApplicationContext(), "Button not ready yet.", Toast.LENGTH_SHORT).show();
        String number = bundle.getString(Contact.numberKey);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));
        Intent chooser = Intent.createChooser(intent, getResources().getString(R.string.smsPrompt));
        startActivity(chooser);
    }

    @Override
    public void onWebButtonPressed(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, webViewFragmentTag);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    //MailForm.OnMailFormFragmentInteractionListener
    @Override
    public void onSendMailButtonPressed(Bundle bundle) {
        String email = bundle.getString(MailForm.emailKey);
        String subject = bundle.getString(MailForm.subjectKey);
        String body = bundle.getString(MailForm.bodyKey);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        Intent chooser = Intent.createChooser(intent, getResources().getString(R.string.mailPrompt));
        startActivity(chooser);
    }

    //WebViewFragment.OnWebViewFragmentInteractionListener
    @Override
    public void onWebViewFragmentBackButtonPressedFromHomeWebsite() {
        showFragment("Contact");
    }
}
