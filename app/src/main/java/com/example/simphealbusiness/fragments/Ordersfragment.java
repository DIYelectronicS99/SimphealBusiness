package com.example.simphealbusiness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simphealbusiness.R;
import com.example.simphealbusiness.adapter.OrderAdapter;
import com.example.simphealbusiness.model.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ordersfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ordersfragment extends Fragment {


    DatabaseReference databaseReference;
    //private RecyclerView recyclerView;

    private OrderAdapter userorderAdapter;
    private int flag = 0;
    private TextView placeorder;

    private EditText uaddress;

    private List<OrderModel> orderlist=new ArrayList<>();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ordersfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ordersfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Ordersfragment newInstance(String param1, String param2) {
        Ordersfragment fragment = new Ordersfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ordersfragment, container, false);









        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        String url = "https://smplmedicalapp-408ea-default-rtdb.firebaseio.com/";
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(url)
                .child("userorders")
                .child(uid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot2 : snapshot.getChildren()){

                    DatabaseReference medref = FirebaseDatabase.getInstance()
                            .getReferenceFromUrl(url).child("userorders")
                            .child(uid)
                            .child(dataSnapshot2.getKey());

                    medref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot snapshot3: snapshot.getChildren()){
                                Log.i("check keys 3", snapshot3.getKey());



                                OrderModel orderModel = snapshot3.getValue(OrderModel.class);

                                if(orderModel.getUstatus().matches("ORDERED")){

                                    Log.i("curuser", String.valueOf(user));
                                    String name = orderModel.getName();
                                    String mName = orderModel.getMedicineName();
                                    String address = orderModel.getAddress();
                                    String amount = orderModel.getAmount();
                                    String phone = orderModel.getPhone();
                                    String tax = orderModel.getTax();
                                    String quantity = orderModel.getQuantity();
                                    String total = String.valueOf(Float.parseFloat(amount) + Float.parseFloat(tax));
                                    String orderID = dataSnapshot2.getKey();
                                    String storeId = orderModel.getStoreId();
                                    String umedID = orderModel.getUmedID();
                                    String UID = orderModel.getUID();
                                    String ustatus = orderModel.getUstatus();

                                    Log.i("storeId", storeId);

                                    Log.i("childval3//amount:-- ", String.valueOf(orderModel.getAmount()));

                                    OrderModel item = new OrderModel(mName, quantity, amount, address,
                                            name, phone, tax, total, orderID, storeId, umedID, UID, ustatus);
                                    orderlist.add(item);

                                    Log.i("orderlist value :--", String.valueOf(orderlist));

                                    //placeorder.setVisibility(View.VISIBLE);
                                   // uaddress.setVisibility(View.VISIBLE);

                                }

                            }


                            RecyclerView recyclerView = view.findViewById(R.id.order_details);
                            recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            userorderAdapter = new OrderAdapter(orderlist);
                            recyclerView.setLayoutManager(layoutManager);
                            //adapter.getFilter().filter(val);
                            recyclerView.setAdapter(userorderAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        return view;
    }
}