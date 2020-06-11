import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.ClassificationType;
import com.google.cloud.automl.v1.Dataset;
import com.google.cloud.automl.v1.LocationName;
import com.google.cloud.automl.v1.OperationMetadata;
import com.google.cloud.automl.v1.TextClassificationDatasetMetadata;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

class LanguageTextClassificationCreateDataset {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    String projectId = "ryangudal-step-2020";
    String displayName = "happy-db";
    createDataset(projectId, displayName);
  }

  // Create a dataset
  static void createDataset(String projectId, String displayName)
      throws IOException, ExecutionException, InterruptedException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (AutoMlClient client = AutoMlClient.create()) {
      // A resource that represents Google Cloud Platform location.
      LocationName projectLocation = LocationName.of(projectId, "us-central1");

      // Specify the classification type
      // Types:
      // MultiLabel: Multiple labels are allowed for one example.
      // MultiClass: At most one label is allowed per example.
      ClassificationType classificationType = ClassificationType.MULTILABEL;

      // Specify the text classification type for the dataset.
      TextClassificationDatasetMetadata metadata =
          TextClassificationDatasetMetadata.newBuilder()
              .setClassificationType(classificationType)
              .build();
      Dataset dataset =
          Dataset.newBuilder()
              .setDisplayName(displayName)
              .setTextClassificationDatasetMetadata(metadata)
              .build();
      OperationFuture<Dataset, OperationMetadata> future =
          client.createDatasetAsync(projectLocation, dataset);

      Dataset createdDataset = future.get();
    }
  }
}