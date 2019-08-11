package senac.macariocalcadosadmin.firebase;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import senac.macariocalcadosadmin.adapters.SelecaoSapatoAdapter;
import senac.macariocalcadosadmin.models.Foto;
import senac.macariocalcadosadmin.models.Sapato;
import senac.macariocalcadosadmin.models.SelecaoSapato;
import senac.macariocalcadosadmin.models.Upload;

public class Database {
    private FirebaseStorage storage;
    private StorageReference sRef;
    private FirebaseDatabase database;
    private DatabaseReference dRef;
    private Context context;
    private StorageTask uploadTask;


    private String name;
    private List<Foto> photos;
    private List<Sapato> sapatos;
    private static int count;

    public Database(Context context, String root)
    {
        this.context = context;
        this.storage = storage;
        this.database = database;
        this.sRef = storage.getInstance().getReference(root);
        this.dRef = database.getInstance().getReference(root);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void insert(final Sapato sapato, final List<Upload> uploads){
        final int size = uploads.size();
        int i = 1;
        if(sapato != null){
            if(!uploads.isEmpty()){
                for(final Upload upload : uploads){
                    name = System.currentTimeMillis() + "." + getFileExtension(upload.getUrl());
                    final StorageReference fRef = sRef.child(name);
                    uploadTask = fRef.putFile(upload.getUrl())
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
                                                        .child(uploadId)
                                                        .setValue(new Foto(name,uri.toString()));
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
            String uploadId = dRef.push().getKey();
            dRef.child(uploadId).setValue(sapato);
            Toast.makeText(context, "Sapato Inserido!", Toast
                    .LENGTH_SHORT).show();
        }
    }

    public void read(final SelecaoSapatoAdapter sapatoAdapter, final List<SelecaoSapato> sapatos, final ProgressBar progressBar){
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sapatos.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final Sapato sapato = ds.getValue(Sapato.class);
                    final ArrayList<Foto> foto = new ArrayList<>();
                    sapato.setFotos(foto);

                    if(sapato != null && sapato.getCodigo() != null && !sapato.getCodigo().isEmpty()){
                        sapatos.add(new SelecaoSapato(sapato));
                        Log.e("SAPATO",sapato.getCodigo());

                        dRef.child("fotos").child(sapato.getCodigo()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
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
