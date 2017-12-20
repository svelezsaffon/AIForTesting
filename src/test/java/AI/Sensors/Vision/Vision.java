package AI.Sensors.Vision;

import AI.Body.BodyUtils;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by santiago on 11/22/17.
 */
public class Vision {

    public List<AnnotateImageResponse> analyzeImage(Image img, Type type) throws IOException {
            ImageAnnotatorClient vision = ImageAnnotatorClient.create();

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();

            Feature feat = Feature.newBuilder().setType(type).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            requests.add(request);

            // Performs label detection on the image file

            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);



            return response.getResponsesList();

    }

    public List<EntityAnnotation> getTextDetectionObject(Image img,String text) throws Exception {


            List<AnnotateImageResponse> responses = this.analyzeImage(img, Type.TEXT_DETECTION);

            List<EntityAnnotation> results=new ArrayList<>();
            responses.forEach(
                    annot -> annot.getTextAnnotationsList().forEach(
                            entity -> {
                                if (text.contains(entity.getDescription())) {
                                    results.add(entity);
                                    entity.getAllFields().forEach((k, v) ->
                                            System.out.printf("%s : %s\n", k, v.toString()));
                                }
                            }
                    )
            );


            return results;

    }

    public EntityAnnotation getImageDetectionObject(Image img,String description) throws Exception {
        EntityAnnotation ret=null;

        List<AnnotateImageResponse> responses = this.analyzeImage(img,Type.IMAGE_PROPERTIES);

        for (AnnotateImageResponse res : responses) {

            if (res.hasError()) {
                throw new Exception(res.getError().getMessage());
            }

            for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                annotation.getAllFields().forEach((k, v)->
                        System.out.printf("%s : %s\n", k, v.toString()));
                if(annotation.getDescription().equals(description)){
                    return annotation;
                }
            }

        }

        return ret;
    }


}
