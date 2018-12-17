package com.example.imransk.loginwithretrofit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imransk.loginwithretrofit.ModelClass.User;
import com.example.imransk.loginwithretrofit.R;

import java.util.List;

public class Users_Adapter extends RecyclerView.Adapter<Users_Adapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public Users_Adapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyler_view_users, parent, false);



        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = userList.get(position);
        Log.e("Name -  - - -", "onBindViewHolder: "+user.getName() );
        holder.name_TV.setText(user.getName());
        holder.email_TV.setText(user.getEmail());
        holder.school_TV.setText(user.getSchool());
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
    //create class that extend ViewHolder class
    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView name_TV;
        TextView email_TV;
        TextView school_TV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name_TV = itemView.findViewById(R.id.texview_name);
            email_TV = itemView.findViewById(R.id.texview_Email);
            school_TV = itemView.findViewById(R.id.texview_school);
        }
    }
}
