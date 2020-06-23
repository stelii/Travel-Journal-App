package com.mycompany.mobile.android.traveljournalapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private EditText nameInput, emailInput, passwordInput ;
    private Button registerButton ;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameInput = view.findViewById(R.id.register_page_name_input);
        emailInput = view.findViewById(R.id.register_page_email_input);
        passwordInput = view.findViewById(R.id.register_page_password_input);
        registerButton = view.findViewById(R.id.register_page_regbutton);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerTheUser();
            }
        });
    }

    private void registerTheUser(){
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                            FirebaseUser newUser = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest changeRequest =
                                    new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).build();
                            newUser.updateProfile(changeRequest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                launchMainActivity();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void launchMainActivity(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
