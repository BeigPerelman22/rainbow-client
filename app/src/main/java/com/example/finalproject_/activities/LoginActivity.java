package com.example.finalproject_.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject_.R;
import com.example.finalproject_.interfaces.TokenAPIInterface;
import com.example.finalproject_.models.AuthTokenModel;
import com.example.finalproject_.utils.LoaderUtils;
import com.example.finalproject_.utils.SharedPreferencesUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.api.services.calendar.CalendarScopes;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private TokenAPIInterface tokenAPIInterface;
    private static final int RC_SIGN_IN = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(getString(R.string.client_id))
                .requestIdToken(getString(R.string.client_id))
                .requestScopes(new Scope(CalendarScopes.CALENDAR))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult();
            handelAccountResponse(account);
        } catch (Exception e) {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void handelAccountResponse(GoogleSignInAccount account) throws JSONException {
        LoaderUtils.showLoader(this);
        RequestQueue volleyQueue = Volley.newRequestQueue(LoginActivity.this);
        String url = getString(R.string.token_api_url);
        AuthTokenModel authTokenModel = createAuthTokenModel(account.getServerAuthCode());
        String jsonAsString = new Gson().toJson(authTokenModel);
        JSONObject jsonObject = new JSONObject(jsonAsString);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        String token = response.getString("access_token");
                        SharedPreferencesUtils.putString(this, "calendar_id", account.getEmail());
                        SharedPreferencesUtils.putString(this, "token", token);
                        launchMainActivity();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                (Response.ErrorListener) error -> {
                    throw new RuntimeException(error.getMessage());
                }
        );

        volleyQueue.add(jsonObjectRequest);
    }

    private void launchMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private AuthTokenModel createAuthTokenModel(String authCode) {
        AuthTokenModel authTokenModel = new AuthTokenModel();
        authTokenModel.setAuthCode(authCode);
        authTokenModel.setClientId(getString(R.string.client_id));
        authTokenModel.setClientSecret(getString(R.string.client_secret));
        authTokenModel.setGrantType(getString(R.string.grant_type));

        return authTokenModel;
    }
}
