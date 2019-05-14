package com.daliproject.ormil.daliproject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import android.graphics.Point;

import java.util.List;


/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class ArActivity extends AppCompatActivity {
    private static final String TAG = ArActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;

    private Uri mModel;

    private ImageButton mImageButton;

    private boolean isShowing = false;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_ux);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        mImageButton = findViewById(R.id.imageBtn);

        String modelPath = getIntent().getExtras().getString(KeyManager.BUNDLE_MARKER_STRING);

        if(!modelPath.equals(""))
            mModel = Uri.parse(modelPath);
        else
            mModel = Uri.parse("https://s3.eu-west-1.amazonaws.com/daliproj/1546976406611-Mesh_Beagle.sfb");


        mImageButton.setOnClickListener(v -> {
            if(!isShowing) {
                addObject();
                isShowing = true;
            }
        });
    }

    private void addObject(){
        Frame frame = arFragment.getArSceneView().getArFrame();
        Point point = getCenterOfScreen();

        if(frame != null){
            List<HitResult> hits = frame.hitTest((float)point.x, (float)point.y);
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                if(trackable instanceof Plane){
                    if(((Plane)trackable).isPoseInPolygon(hit.getHitPose())){
                        placeObject(hit.createAnchor(), mModel);
                    }
                }
            }
        }
    }

    private void placeObject(Anchor anchor, Uri model){
        ModelRenderable.builder()
                .setSource(this, model)
                .build()
                .thenAccept(renderable -> addNodeToScene(arFragment, anchor, renderable))
                .exceptionally(
                        throwable -> {
                            Log.d("ARDownload", throwable.toString());
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable){
        if (renderable == null) {
            return;
        }

        AnchorNode anchorNode = new AnchorNode(anchor);

        // Create the transformable model and add it to the anchor.
        TransformableNode modelTransform = new TransformableNode(arFragment.getTransformationSystem());
        modelTransform.setParent(anchorNode);
        modelTransform.setRenderable(renderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        modelTransform.select();
        modelTransform.setLocalScale(new Vector3(0.25f, 0.25f, 0.25f));
    }

    private Point getCenterOfScreen(){
        View view = findViewById(android.R.id.content);
        return new Point(view.getWidth() / 2, view.getHeight() / 2);
    }

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
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