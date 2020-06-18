package com.google.sps.data;

import com.google.cloud.automl.v1.AnnotationPayload;
import com.google.cloud.automl.v1.ExamplePayload;
import com.google.cloud.automl.v1.ModelName;
import com.google.cloud.automl.v1.PredictRequest;
import com.google.cloud.automl.v1.PredictResponse;
import com.google.cloud.automl.v1.PredictionServiceClient;
import com.google.cloud.automl.v1.TextSnippet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LanguageTextClassificationPredict {
    private String projectId;
    private String modelId;

  public LanguageTextClassificationPredict() {
    this.projectId = "ryangudal-step";
    this.modelId = "TCN4253511309586333696";
  }

  public List<String> predict(String content) throws IOException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (PredictionServiceClient client = PredictionServiceClient.create()) {
      // Get the full path of the model.
      ModelName name = ModelName.of(projectId, "us-central1", modelId);
      TextSnippet textSnippet =
          TextSnippet.newBuilder()
              .setContent(content)
              .setMimeType("text/plain") // Types: text/plain, text/html
              .build();
      ExamplePayload payload = ExamplePayload.newBuilder().setTextSnippet(textSnippet).build();
      PredictRequest predictRequest =
          PredictRequest.newBuilder().setName(name.toString()).setPayload(payload).build();

      PredictResponse response = client.predict(predictRequest);

      List<String> classification = new ArrayList<>();
      for (AnnotationPayload annotationPayload : response.getPayloadList()) {
        classification.add(annotationPayload.getDisplayName());
      }
      return classification;
    }
  }
}