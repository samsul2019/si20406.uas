package uas.alfifadilahhardianto.alfiuasfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uas.alfifadilahhardianto.alfiuasfirebase.paket.AdapterDataku;
import uas.alfifadilahhardianto.alfiuasfirebase.paket.Dataku;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton TombolData;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Dataku ");
    List<Dataku> list = new ArrayList<>();
    AdapterDataku adapterDataku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TombolData = findViewById(R.id.tombol_data);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TombolData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTambahData();
            }
        });

        BacaData();
    }

    private void BacaData() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Dataku value = snapshot.getValue(Dataku.class);
                    list.add(value);
                }
                adapterDataku = new AdapterDataku(MainActivity.this,list );
                recyclerView.setAdapter(adapterDataku);

                setClick();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void setClick() {
        adapterDataku.setOncallBack(new AdapterDataku.OncallBack() {
            @Override
            public void onTombolEdit(Dataku dataku) {
                showDialogEditData(dataku);

            }

            @Override
            public void onTombolHapus(Dataku dataku) {
                HapusData(dataku);

            }
        });
    }

    private void showDialogEditData(Dataku dataku) {
        Dialog dialog = new Dialog(this );
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data_layout);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp =new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton TombolKeluar = dialog.findViewById(R.id.tombol_keluar);
        TombolKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText TxtTambah =dialog.findViewById(R.id.txt_tambah);
        Button TombolTambah = dialog.findViewById(R.id.tombol_tambah);
        TextView TvTambah = dialog.findViewById(R.id.tv_tambah);

        TxtTambah.setText(dataku.getIsi());
        TombolTambah.setText("Update");
        TvTambah.setText("Edit Data");

        TombolTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(TxtTambah.getText())){
                    TombolTambah.setError("Silahkan Isi Data");

                }else {
                    EditData(dataku, TxtTambah.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void EditData(Dataku dataku, String baru) {
        myRef.child(dataku.getKunci()).child("isi").setValue(baru).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Update Berhasil",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void HapusData(Dataku dataku) {
        myRef.child(dataku.getKunci()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(),dataku.getIsi()+ " Terhapus",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showDialogTambahData() {
        Dialog dialog = new Dialog(this );
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data_layout);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp =new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        ImageButton TombolKeluar = dialog.findViewById(R.id.tombol_keluar);
        TombolKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText TxtTambah =dialog.findViewById(R.id.txt_tambah);
        Button TombolTambah = dialog.findViewById(R.id.tombol_tambah);

        TombolTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(TxtTambah.getText())){
                    TombolTambah.setError("Silahkan Isi Data");

                }else {
                    SimpanData(TxtTambah.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void SimpanData(String s) {

        String Kunci = myRef.push().getKey();
        Dataku dataku = new Dataku(Kunci, s);

        myRef.child(Kunci).setValue(dataku).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
            }
        });
    }
}