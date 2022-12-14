package com.example.planner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMySolvingImgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMySolvingImgFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // 변수 선언
    ImageView btn_camera;
    ImageView btn_image;
    Bitmap bitmap;
    Uri uri;

    ImageView imageView;
    Button next;
    ProblemObj problemObj = new ProblemObj();

    FirebaseAuth mAuth;
    List<String> categories = new ArrayList<>();
    String name = "";

    private final String TAG = "AddMySolvingImgFragment";


    public AddMySolvingImgFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMySolvingIMGFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMySolvingImgFragment newInstance(String param1, String param2) {
        AddMySolvingImgFragment fragment = new AddMySolvingImgFragment();
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
            problemObj = (ProblemObj) getArguments().getSerializable("bundlepro3");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_my_solving_i_m_g, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        btn_camera = (ImageView) view.findViewById(R.id.btn_camera);
        btn_image = (ImageView) view.findViewById(R.id.btn_image);
        next = (Button) view.findViewById(R.id.next);

        mAuth = FirebaseAuth.getInstance();

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultPicture.launch(intent);

//                if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
//                    if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        activityResultPicture.launch(intent);
//                    }
//                    else {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        activityResultPicture.launch(intent);
//                }}
//
            }
        });

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date now = new Date();
                final String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(now);
                problemObj.addMySolving(time);

                //Log 확인
                Log.d("problemObj", "MySolvingIMG : " + problemObj.getMySolving());



                // Get a default Storage bucket
                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Points to the root reference
                StorageReference storageRef = storage.getReference();

                // Create a reference to 'images/mountains.jpg'
                StorageReference mountainImagesRef = storageRef.child(time);
                //StorageReference mountainImagesRef = storageRef.child(getPath("jpg"));

                //for test
                //StorageReference mountainImagesRef = storageRef.child("yWXNknPkHgUnph1iV29H0fe97LV2/mountains.jpg"); // 로그인 안하고 시도
                //StorageReference mountainImagesRef = storageRef.child("public/mountains.jpg"); // 로그인 하고 시도

                // Get the data from an ImageView as bytes
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //0-100
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainImagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.d(TAG, "이미지뷰의 이미지 업로드 실패");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Log.d(TAG, "이미지뷰의 이미지 업로드 성공");

//                        launchDownloadActivity(taskSnapshot.getMetadata().getReference().toString());
                    }
                });

                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();

                Log.d("problemObj", "category : " + problemObj.getCategory());
                Log.d("problemObj", "name : " + problemObj.getProblemName());
                Log.d("problemObj", "cycle : " + problemObj.getCycle().toString());
                Log.d("problemObj", "tag : " + problemObj.getReviewTag().toString());
                Log.d("problemObj", "MySolvingImg : " + problemObj.getMySolving());

                // 파이어베이스에서 카테고리가 selectedCategory인 컬렉션에 들어가서 문제 해시맵<이름, 객체>로 가져오기
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.document("user/" + uid).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        categories = (List) document.get("categories");
                        if(!categories.contains(problemObj.getCategory())){
                            categories.add(problemObj.getCategory());
                        }
                        name = (String) document.get("name");
                        HashMap<String, Object> cf = new HashMap<>();

                        cf.put("categories", categories);
                        cf.put("id", user.getEmail());
                        cf.put("name", name);
                        db.document("user/" + uid).set(cf);
                    }
                });

//                HashMap<Object,Object> hashMap = new HashMap<>();
//
//                hashMap.put("category", problemObj.getCategory());
//                hashMap.put("cycle", problemObj.getCycle());
//                hashMap.put("mySolving", problemObj.getMySolving());
//                hashMap.put("ox", problemObj.getOX());
//                hashMap.put("problemImg", problemObj.getProblemImg());
//                hashMap.put("problemName", problemObj.getProblemName());
//                hashMap.put("reviewCnt", problemObj.getReviewCnt());
//                hashMap.put("reviewDay", problemObj.getReviewDay());
//                hashMap.put("reviewTag", problemObj.getReviewTag());
//                hashMap.put("solutionImg", problemObj.getSolutionImg());
//
//                db.document("user/" + uid + "/" + problemObj.getCategory() + "/" + problemObj.getProblemName()).set(hashMap);


                //NEXT VIEW
                Bundle result = new Bundle();
                result.putSerializable("bundlebundle", problemObj);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                AddCycleAgainFragment addCycleAgainFragment = new AddCycleAgainFragment();
                addCycleAgainFragment.setArguments(result);
                transaction.replace(R.id.new_fragment, addCycleAgainFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
            private String getPath(String extension) {
                String uid = getUidOfCurrentUser();

                String dir = (uid != null) ? uid : "public";

                String fileName = (uid != null) ? (uid + "_" + System.currentTimeMillis() + "." + extension)
                        : ("anonymous" + "_" + System.currentTimeMillis() + "." + extension );

                return dir + "/" + fileName;
            }

            //            private void launchDownloadActivity(String referenceForDownload) {
//                Intent intent = new Intent(this, DownloadActivity.class);
//                intent.putExtra("DOWNLOAD_REF", referenceForDownload);
//                startActivity(intent);
//            }
            private boolean hasSignedIn() {
                return FirebaseAuth.getInstance().getCurrentUser() != null ? true : false;
            }

            private String getUidOfCurrentUser() {
                return hasSignedIn() ? FirebaseAuth.getInstance().getCurrentUser().getUid() : null;
            }

        });
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {

                        Bundle extras = result.getData().getExtras();
                        bitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
    );
}