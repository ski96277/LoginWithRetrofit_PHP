package com.example.imransk.loginwithretrofit.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imransk.loginwithretrofit.ModelClass.User;
import com.example.imransk.loginwithretrofit.ModelClass.UsersResponse;
import com.example.imransk.loginwithretrofit.R;
import com.example.imransk.loginwithretrofit.adapter.Users_Adapter;
import com.example.imransk.loginwithretrofit.api.RetofitClient_SingleTone;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment{

    RecyclerView recyclerView;
    private Users_Adapter adapter;
    private List<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView=view.findViewById(R.id.recylerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<UsersResponse> call= RetofitClient_SingleTone.getInstance().getApi().getUsers();

        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {

                userList = response.body().getUsers();
                adapter=new Users_Adapter(getActivity(),userList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }
}
