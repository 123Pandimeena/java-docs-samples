/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cloudrun.snippets.services;

// [START cloudrun_create_service_LRO]
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.run.v2.Container;
import com.google.cloud.run.v2.CreateServiceRequest;
import com.google.cloud.run.v2.LocationName;
import com.google.cloud.run.v2.RevisionTemplate;
import com.google.cloud.run.v2.Service;
import com.google.cloud.run.v2.ServicesClient;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CreateServiceLRO {

  public static void main(String[] args)
      throws IOException, InterruptedException, ExecutionException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "your-project-id";
    String location = "us-central1";
    String serviceId = "my-service-id";
    String imageUrl = "us-docker.pkg.dev/cloudrun/container/hello";
    createServiceLRO(projectId, location, serviceId, imageUrl);
  }

  public static void createServiceLRO(String projectId, String location, String serviceId, String imageUrl)
      throws IOException, InterruptedException, ExecutionException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests.
    try (ServicesClient servicesClient = ServicesClient.create()) {
      // Define service
      // Shows minimum necessary configuration
      Service service =
          Service.newBuilder()
              .setTemplate(
                  RevisionTemplate.newBuilder()
                      .addContainers(Container.newBuilder().setImage(imageUrl)))
              .build();

      CreateServiceRequest request =
          CreateServiceRequest.newBuilder()
              .setParent(LocationName.of(projectId, location).toString())
              .setServiceId(serviceId)
              .setService(service)
              .build();
      // Send request
      OperationFuture<Service, Service> future =
          servicesClient.createServiceOperationCallable().futureCall(request);
      // Do something.
      Service response = future.get();
      // Example usage of the Service object
      System.out.println("Created service: " + response.getName());
      System.out.println("With spec:\n" + response.getTemplate());
    }
  }
}
// [END cloudrun_create_service_LRO]
