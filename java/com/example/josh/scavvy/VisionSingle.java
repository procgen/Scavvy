package com.example.josh.scavvy;

import android.graphics.Bitmap;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class VisionSingle {
    private static final VisionSingle ourInstance = new VisionSingle();

    public Vision v;
    public Feature feature;

    public static VisionSingle getInstance() {
        return ourInstance;
    }

    private VisionSingle() {
        Vision.Builder visionBuilder = new Vision.Builder(
                new NetHttpTransport(),
                new AndroidJsonFactory(),
                null);

        visionBuilder.setApplicationName("Scavvy");

        visionBuilder.setVisionRequestInitializer(new VisionRequestInitializer("AIzaSyAa0t0ndoCnQH9J9SITHK404LCNooMEgTI"));
        v = visionBuilder.build();
        feature = new Feature();
        feature.setType("LABEL_DETECTION");
    }

    public List<EntityAnnotation> visionRequest(Bitmap requestImage) throws IOException
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        requestImage.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        byte[] byteArray = stream.toByteArray();

        Image inputImage = new Image();
        inputImage.encodeContent(byteArray);

        AnnotateImageRequest request = new AnnotateImageRequest();
        request.setImage(inputImage);
        request.setFeatures(Arrays.asList(feature));

        BatchAnnotateImagesRequest batchRequest =
                new BatchAnnotateImagesRequest();

        batchRequest.setRequests(Arrays.asList(request));

        BatchAnnotateImagesResponse batchResponse =
                v.images().annotate(batchRequest).execute();

        List<EntityAnnotation> labels = batchResponse.getResponses()
                .get(0).getLabelAnnotations();

        return labels;
    }
}
