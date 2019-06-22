package com.ormil.daliproject.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.Adapters.CardAdapter;
import com.ormil.daliproject.Models.ArtworkModel;
import com.ormil.daliproject.Models.GenreMonitorModel;
import com.ormil.daliproject.R;
import com.ormil.daliproject.Services.ExitService;
import com.ormil.daliproject.Services.HttpService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArActivity extends AppCompatActivity {

    private static final String TAG = "ArActivity";
    public static final String CURRENT_LOCATION_KEY = "currnet_Location";

    public static final int ACTIVITY_NUMBER=3;
    private static final double MIN_OPENGL_VERSION = 3.0;

    private LatLng currentLocation;

    private ArFragment arFragment;
    private AnchorNode anchorNode;

    private boolean isArtPlaced = false;
    private boolean isArtChanged = false;
    private int selectedArt;

    private ViewPager viewPager;
    private CardAdapter cardAdapter;
    //private List<CardModel> cardModels;
    private ArrayList<ArtworkModel> artworksModels;

    private GenreMonitorModel monitorModel;
    private long startTime = 0;
    private long endTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserMonitorHelper.screens.add(ACTIVITY_NUMBER);
        Intent intent = new Intent(this, ExitService.class);
        startService(intent);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        currentLocation = getIntent().getExtras().getParcelable(CURRENT_LOCATION_KEY);

        setContentView(R.layout.activity_ar);

        try {
            String artworksJson = HttpService.getArtworkByLocation(currentLocation.latitude, currentLocation.longitude);

            Gson g = new Gson();
            Type listType = new TypeToken<ArrayList<ArtworkModel>>() {}.getType();
            artworksModels = g.fromJson(artworksJson,  listType);
            Log.d(TAG, "artworkList: " + artworksModels.toString() );
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
            Log.e(TAG,e.getMessage());
            Log.e(TAG, "Error while looking for artworks");
        }

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

        cardAdapter = new CardAdapter(artworksModels, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(cardAdapter);
        viewPager.setPadding(130, 0, 130, 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                Log.e(TAG, "Page Changed to: " + i);
                isArtChanged = true;
                selectedArt = i;

                endTime = new Date().getTime();
                monitorModel.setTime((endTime - startTime) /1000f);
                if(startTime >= 0 && monitorModel != null) {
                    UserMonitorHelper.genreMonitorModels.add(monitorModel);
                    Log.d(TAG, "Switch: " + monitorModel);
                }
                startTime = 0;
                endTime = 0;
                monitorModel = null;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            if(isArtChanged || !isArtPlaced)
                addObject("http://" + artworksModels.get(selectedArt).getPath());
        });
    }

    private void addObject(String modelPath) {
        Log.d(TAG, "addObject() called with: modelPath = [" + modelPath + "]");
        isArtChanged = false;

        Uri model = modelPath.endsWith(".gltf") ? Uri.parse("https://poly.googleusercontent.com/downloads/0BnDT3T1wTE/85QOHCZOvov/Mesh_Beagle.gltf") : Uri.parse(modelPath);
        Frame frame = arFragment.getArSceneView().getArFrame();
        Point point = getScreenCenter();
        List<HitResult> hitResults;

        isArtPlaced = false;

        RenderableSource.SourceType fileType = modelPath.endsWith(".gltf") ? RenderableSource.SourceType.GLTF2 : RenderableSource.SourceType.GLB;

        if(frame != null) {
            hitResults = frame.hitTest(point.x, point.y);
            for (HitResult hit : hitResults) {
                Trackable trackable = hit.getTrackable();
                if(trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    isArtPlaced = true;
                    placeArtwork(hit.createAnchor(), model, fileType);
                    break;
                }
            }
        }
    }

    private void placeArtwork(Anchor anchor, Uri model, RenderableSource.SourceType fileType) {
        Log.d(TAG, "placeArtwork() called with: anchor = [" + anchor + "], model = [" + model + "], fileType = [" + fileType + "]");
        ModelRenderable.builder()
                .setSource(this,
                        RenderableSource.builder().setSource(this, model, fileType).build())
                .build()
                .thenAccept(renderable -> addNodeToScene(anchor, renderable))
                .exceptionally(throwable -> {
                    Toast toast = Toast.makeText(this, "Unable to load artwork", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });
    }

    private void addNodeToScene(Anchor anchor, Renderable renderable) {
        Log.d(TAG, "addNodeToScene() called with: anchor = [" + anchor + "], renderable = [" + renderable + "]");
        if(renderable == null)
            return;

        if(anchorNode != null)
            arFragment.getArSceneView().getScene().removeChild(anchorNode);

        anchorNode = new AnchorNode(anchor);

        TransformableNode modelTransform = new TransformableNode(arFragment.getTransformationSystem());
        modelTransform.setParent(anchorNode);
        modelTransform.setRenderable(renderable);
        modelTransform.getScaleController().setMaxScale(0.6f);
        modelTransform.getScaleController().setMinScale(0.2f);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        modelTransform.select();

        startTime = new Date().getTime();
        ArtworkModel artworkModel = artworksModels.get(selectedArt);
        monitorModel = new GenreMonitorModel(artworkModel.getId(), artworkModel.getGeneresIds().get(0));
    }

    private Point getScreenCenter() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new Point(size.x/2, size.y/2);
    }


    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
