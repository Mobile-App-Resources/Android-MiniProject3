package com.tistory.puzzleleaf.androidminiproject3.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.tistory.puzzleleaf.androidminiproject3.MainActivity;
import com.tistory.puzzleleaf.androidminiproject3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cmtyx on 2017-07-16.
 */

public class AddFragment extends BaseFragment {

    final int SEARCHADDRESS = 1000;
    //EditText
    @BindView(R.id.add_name) EditText name; @BindView(R.id.add_number) EditText number;
    @BindView(R.id.add_address) TextView address; @BindView(R.id.add_description) EditText description;
    @BindView(R.id.add_description_count) TextView countView;
    //하단 버튼
    @BindView(R.id.add_next)
    Button next; @BindView(R.id.add_prev) Button prev;

    private String latitude;
    private String longitude;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add,container,false);
        ButterKnife.bind(this,view);
        viewInit();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCHADDRESS) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                address.setText(place.getAddress().toString());
                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);

            } else {
                address.setText("");
                Toast.makeText(getContext(),"에러 발생",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void viewInit(){
        textCountSet(); // Count Init
        //카운트 이벤트 설정
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                textCountSet();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.add_address)
    public void addAddress(){
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
            startActivityForResult(intent, SEARCHADDRESS);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
            Log.d("qwe",e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            Log.d("qwe",e.getMessage());
        }
    }

    //데이터가 있는지 없는지 검증
    private boolean isData(){
        if(name.length()>0 && address.length()>0 && number.length()>0 && description.length()>0){
            return true;
        }
        else{
            return false;
        }
    }
    private void dataClear(){
        number.setText("");
        address.setText("");
        number.setText("");
        description.setText("");
    }

    //텍스트 카운트 설정
    private void textCountSet(){
        countView.setText(getResources().getString(R.string.text_cnt) +" "+ String.valueOf(description.length())
                + getResources().getString(R.string.text_cnt_max));
    }

    //하단 버튼
    @OnClick(R.id.add_prev)
    public void prev(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.add_next)
    public void nextBtn(){
        if(isData()) {
            MainActivity.dbHelper.insert(name.getText().toString(), address.getText().toString(), number.getText().toString(),
                    description.getText().toString(), latitude, longitude);
            dataClear();
            startFragment(MapFragment.class);
        }
        else{
            Toast.makeText(getContext(),"데이터를 입력해주세요",Toast.LENGTH_SHORT).show();
        }
    }
}
