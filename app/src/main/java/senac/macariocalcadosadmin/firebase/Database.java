package senac.macariocalcadosadmin.firebase;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import senac.macariocalcadosadmin.adapters.SelecaoSapatoAdapter;
import senac.macariocalcadosadmin.models.Foto;
import senac.macariocalcadosadmin.models.Sapato;
import senac.macariocalcadosadmin.models.SelecaoSapato;
import senac.macariocalcadosadmin.models.Upload;

public class Database {
    private StorageReference sRef;
    private DatabaseReference dRef;
    private Context context;

    public Database(Context context, String root)
    {
        this.context = context;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.sRef = storage.getReference(root);
        this.dRef = database.getReference(root);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void insert(final Sapato sapato, final List<Upload> uploads){
        if(sapato != null){
            if(!uploads.isEmpty()){
                for(final Upload upload : uploads){
                    String name = System.currentTimeMillis() + "." + getFileExtension(upload.getUrl());
                    final StorageReference fRef = sRef.child(name);
                    fRef.putFile(upload.getUrl())
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()){
                                        fRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String uploadId = dRef.child("fotos")
                                                        .child(sapato.getCodigo())
                                                        .push()
                                                        .getKey();

                                                    dRef.child("fotos").child(sapato.getCodigo())
                                                            .child(Objects.requireNonNull(uploadId))
                                                            .setValue(new Foto(fRef.getName(),uri.toString()));
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
            sapato.setKey(dRef.push().getKey());
            dRef.child(sapato.getKey()).setValue(sapato);
            Toast.makeText(context, "Sapato Inserido!", Toast
                    .LENGTH_SHORT).show();
        }
    }

    public void delete(final Sapato sapato){
        for(Foto f : sapato.getFotos() ){
            StorageReference imageRef = sRef.child(f.getNome());
            imageRef.delete();
        }
        dRef.child("fotos").child(sapato.getCodigo()).removeValue();
        dRef.child(sapato.getKey()).removeValue();
        //adapter.notifyDataSetChanged();
    }

    public void update(@NonNull Sapato novo){
        dRef.child("fotos").child(novo.getCodigo()).removeValue();
        novo.setCodigo();

        List<Foto> temp = novo.getFotos();
        novo.setFotos(null);

        if(!temp.isEmpty()){
            for(Foto foto : temp){
                String uploadId = dRef.child("fotos")
                        .child(novo.getCodigo())
                        .push()
                        .getKey();
                dRef.child("fotos").child(novo.getCodigo()).child(uploadId).setValue(foto);
            }
        }
        dRef.child(novo.getKey()).setValue(novo);

        Toast.makeText(context, "Sapato Inserido!", Toast
                    .LENGTH_SHORT).show();
    }

    public void read(final SelecaoSapatoAdapter sapatoAdapter, final List<SelecaoSapato> sapatos, final ProgressBar progressBar){
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                sapatos.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final Sapato sapato = ds.getValue(Sapato.class);
                    final ArrayList<Foto> foto = new ArrayList<>();
                    Objects.requireNonNull(sapato).setFotos(foto);

                    if (sapato.getCodigo() != null && !sapato.getCodigo().isEmpty()) {
                        sapatos.add(new SelecaoSapato(sapato));
                        dRef.child("fotos").child(sapato.getCodigo()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    sapato.addFoto(ds.getValue(Foto.class));
                                }
                                sapatoAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                sapatoAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
