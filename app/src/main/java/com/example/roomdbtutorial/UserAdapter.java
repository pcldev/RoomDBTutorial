package com.example.roomdbtutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> mListUser;
    private IClickItemUser iClickItemUser;
    public interface IClickItemUser{
        void updateUser(User user);

    }

    public UserAdapter(IClickItemUser iClickItemUser) {
        this.iClickItemUser = iClickItemUser;
    }

    public void setData(List<User> list){
        this.mListUser = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // tạo view bằng inflate đến user_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUserName;
        private TextView tvAddress;
        private Button btnUpdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // ánh xạ đến textview của user
            tvUserName = itemView.findViewById(R.id.tv_username);
            tvAddress = itemView.findViewById(R.id.tv_address);
            btnUpdate = itemView.findViewById(R.id.btn_update);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        //lay ra current user bằng cách get position
        final User user = mListUser.get(position);
        if(user == null){
            return;
        }
        //set text cho mỗi text view
        holder.tvUserName.setText(user.getUsername());

        holder.tvAddress.setText(user.getAddress());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemUser.updateUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListUser != null){
            return mListUser.size();
        }
        return 0;
    }


}
