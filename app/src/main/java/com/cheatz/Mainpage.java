package com.cheatz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;



public class Mainpage extends AppCompatActivity {

    TextView title;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT1 = "text";
    public static final String TEXT2 = "text2";
    public static final String TEXT3 = "text3";
    public static final String TEXT4 = "text4";
    FirebaseFirestore firestore;
    ImageView home,tools,download,questionbank,lab,workflow,Recycle,importantformulas;
    private MainpageRecyclerAdapter notificationsAdapterx;
    private List<Mainpagemodel> NotifListx;
    private MainpageRecyclerAdapter notificationsAdaptery;
    private List<Mainpagemodel> NotifListy;
    private MainpageRecyclerAdapter notificationsAdapterz;
    private List<Mainpagemodel> NotifListz;
    ConstraintLayout Constrainlayouthundered;
    ImageView arrowhundred;
    ConstraintLayout Constanlayoutfifityplus;
    ImageView arrowrightfifty,arrowleftfifity;
    ConstraintLayout Constrainlayoutpass;
    ImageView arrowpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        importantformulas=findViewById(R.id.imageView8);
        Recycle=findViewById(R.id.imageView11);
        Recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mainpage.this,RecycleActivity.class);
                startActivity(intent);
            }
        });
        workflow=findViewById(R.id.imageView9);
        workflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mainpage.this,Workflow.class);
                startActivity(intent);
            }
        });
        title=findViewById(R.id.textView17);
        home=findViewById(R.id.imageView2);
        tools=findViewById(R.id.imageView5);
        lab=findViewById(R.id.imageView10);
        download=findViewById(R.id.imageView6);
        questionbank=findViewById(R.id.imageView7);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mainpage.this,Homepage.class);
                startActivity(intent);
            }
        });
        Constrainlayouthundered=findViewById(R.id.hunderprecentageconstrainlayout);
        arrowhundred=findViewById(R.id.imageView3);
        Constanlayoutfifityplus=findViewById(R.id.fiftyconstrainlayout);
        arrowrightfifty=findViewById(R.id.imageView4fifty);
        arrowleftfifity=findViewById(R.id.imageView3fifty);
        Constrainlayoutpass=findViewById(R.id.passconstrainlayout);
        arrowpass=findViewById(R.id.imageView4pass);
        arrowhundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constrainlayouthundered.setVisibility(View.GONE);
                Constrainlayoutpass.setVisibility(View.GONE);
                Constanlayoutfifityplus.setVisibility(View.VISIBLE);
            }
        });
        arrowleftfifity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Constanlayoutfifityplus.setVisibility(View.GONE);
               Constrainlayouthundered.setVisibility(View.GONE);
               Constrainlayoutpass.setVisibility(View.VISIBLE);
            }
        });
        arrowrightfifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constanlayoutfifityplus.setVisibility(View.GONE);
                Constrainlayouthundered.setVisibility(View.VISIBLE);
                Constrainlayoutpass.setVisibility(View.GONE);
            }
        });
        arrowpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constanlayoutfifityplus.setVisibility(View.VISIBLE);
                Constrainlayouthundered.setVisibility(View.GONE);
                Constrainlayoutpass.setVisibility(View.GONE);
            }
        });

        firestore = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String branchname = sharedPreferences.getString(TEXT1, "");
        String subbranchname = sharedPreferences.getString(TEXT2, "");
        String year = sharedPreferences.getString(TEXT3, "");
        String sem = sharedPreferences.getString(TEXT4, "");
        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null)
        {
            String subjectname = bundle1.get("subjectname").toString();
            title.setText(branchname+"\n"+subbranchname+"\n"+sem+"\n"+year+"\n"+subjectname);
            questionbank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Intent = new Intent(Mainpage.this, Questionbank.class);
                    Intent.putExtra("subjectname",subjectname);
                    startActivity(Intent);
                }
            });
            importantformulas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Mainpage.this,ImportantActivity.class);
                    intent.putExtra("subjectname",subjectname);
                    startActivity(intent);
                }
            });

            NotifListx = new ArrayList<>();
            RecyclerView notificationList = findViewById(R.id.recyclerView);
            notificationsAdapterx = new MainpageRecyclerAdapter(NotifListx);
            notificationList.setHasFixedSize(true);
            notificationList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            notificationList.setAdapter(notificationsAdapterx);
            firestore = FirebaseFirestore.getInstance();
            firestore.collection(branchname+subbranchname+sem+year+subjectname+"100").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for(DocumentChange doc: documentSnapshots.getDocumentChanges()) {
                        Mainpagemodel notifications = doc.getDocument().toObject(Mainpagemodel.class);
                        NotifListx.add(notifications);
                        notificationsAdapterx.notifyDataSetChanged();
                    }
                }
            });


            NotifListy = new ArrayList<>();
            RecyclerView notificationList3 = findViewById(R.id.recyclerViewfifty);
            notificationsAdaptery = new MainpageRecyclerAdapter(NotifListy);
            notificationList3.setHasFixedSize(true);
            notificationList3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            notificationList3.setAdapter(notificationsAdaptery);
            firestore = FirebaseFirestore.getInstance();
            firestore.collection(branchname+subbranchname+sem+year+subjectname+"50+").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for(DocumentChange doc: documentSnapshots.getDocumentChanges()) {
                        Mainpagemodel notifications = doc.getDocument().toObject(Mainpagemodel.class);
                        NotifListy.add(notifications);
                        notificationsAdaptery.notifyDataSetChanged();
                    }
                }
            });

            
            NotifListz = new ArrayList<>();
            RecyclerView notificationList2 = findViewById(R.id.recyclerViewpass);
            notificationsAdapterz = new MainpageRecyclerAdapter(NotifListz);
            notificationList2.setHasFixedSize(true);
            notificationList2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            notificationList2.setAdapter(notificationsAdapterz);
            firestore = FirebaseFirestore.getInstance();
            firestore.collection(branchname+subbranchname+sem+year+subjectname+"pass").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for(DocumentChange doc: documentSnapshots.getDocumentChanges()) {
                        Mainpagemodel notifications = doc.getDocument().toObject(Mainpagemodel.class);
                        NotifListz.add(notifications);
                        notificationsAdapterz.notifyDataSetChanged();
                    }
                }
            });

            // logic error fix
            firestore.collection(branchname+subbranchname+sem+year+subjectname+"Tools").document("tools").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            String notesurl = task.getResult().getString("notesurl");
                            String toolsurl = task.getResult().getString("toolsurl");
                            String laburl = task.getResult().getString("laburl");
                            if(laburl!=null&& laburl.equals(""))
                            {
                                lab.setVisibility(View.VISIBLE);
                                lab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent Intent = new Intent(Mainpage.this, Lab.class);
                                        Intent.putExtra("subjectname",subjectname);
                                        startActivity(Intent);
                                    }
                                });
                            }
                            tools.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(toolsurl));
                                    startActivity(browserIntent);
                                }
                            });
                            download.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notesurl));
                                    startActivity(browserIntent);
                                }
                            });
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Mainpage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}