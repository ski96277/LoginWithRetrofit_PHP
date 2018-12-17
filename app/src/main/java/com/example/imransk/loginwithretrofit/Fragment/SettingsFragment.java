package com.example.imransk.loginwithretrofit.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imransk.loginwithretrofit.ModelClass.DefaultResponse;
import com.example.imransk.loginwithretrofit.ModelClass.UpdateResponse;
import com.example.imransk.loginwithretrofit.ModelClass.User;
import com.example.imransk.loginwithretrofit.R;
import com.example.imransk.loginwithretrofit.Storage.SharedPrefManager;
import com.example.imransk.loginwithretrofit.activities.LoginActivity;
import com.example.imransk.loginwithretrofit.activities.MainActivity;
import com.example.imransk.loginwithretrofit.api.RetofitClient_SingleTone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextSchool;
    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;

    private Button button_Save;
    private Button button_Change_Password;
    private Button button_Log_out;
    private Button button_Delete;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextName = view.findViewById(R.id.editTextName);
        editTextSchool = view.findViewById(R.id.editTextSchool);
        editTextCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);

        button_Save = view.findViewById(R.id.buttonSave);
        button_Change_Password = view.findViewById(R.id.buttonChangePassword);
        button_Log_out = view.findViewById(R.id.buttonLogout);
        button_Delete = view.findViewById(R.id.buttonDelete);

        button_Save.setOnClickListener(this);
        button_Change_Password.setOnClickListener(this);
        button_Log_out.setOnClickListener(this);
        button_Delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonSave:
                UpdateProfile();
                break;
            case R.id.buttonChangePassword:
                UpdatePassword();
                break;
            case R.id.buttonLogout:
                logOut();
                break;
            case R.id.buttonDelete:
                deleteUser();
                break;

        }
    }

    private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are You Sure");
        builder.setMessage("This action is irreversible");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User user = SharedPrefManager.getInstance(getActivity()).getUser();

                Call<DefaultResponse> call = RetofitClient_SingleTone.getInstance().getApi().deleteUser(user.getId());
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if (!response.body().isErr()){
                            SharedPrefManager.getInstance(getActivity()).clear();
                            Intent intent=new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }

    private void logOut() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void UpdatePassword() {
        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        if (currentPassword.isEmpty()) {
            editTextCurrentPassword.setError("password required");
            editTextCurrentPassword.requestFocus();
            return;
        }
        if (newPassword.isEmpty()) {
            editTextNewPassword.setError("enter new password");
            editTextNewPassword.requestFocus();
            return;
        }
        User user = SharedPrefManager.getInstance(getActivity()).getUser();


        Call<DefaultResponse> call = RetofitClient_SingleTone.getInstance().getApi().UpdatePassword
                (currentPassword, newPassword, user.getEmail());

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }

    private void UpdateProfile() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String school = editTextSchool.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (school.isEmpty()) {
            editTextSchool.setError("School required");
            editTextSchool.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        Call<UpdateResponse> call = RetofitClient_SingleTone.getInstance()
                .getApi()
                .updateUser(user.getId(), email, name, school);

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (!response.body().isError()) {
                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
                    editTextEmail.setText("");
                    editTextName.setText("");
                    editTextSchool.setText("");
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {

            }
        });
    }
}
