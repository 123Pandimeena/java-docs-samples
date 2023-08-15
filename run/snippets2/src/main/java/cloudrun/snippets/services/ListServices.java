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

import java.io.IOException;
import java.time.Instant;
// [START cloudrun_Services_ListServices_Locationname_sync]
import com.google.cloud.run.v2.LocationName;
import com.google.cloud.run.v2.Service;
import com.google.cloud.run.v2.ServicesClient;
import com.google.protobuf.Timestamp;

public class ListServices {

  public static void main(String[] args) throws IOException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "PROJECT";
    String location = "us-central1";
    syncListServicesLocationname(projectId, location);
  }

  public static void syncListServicesLocationname(String projectId, String location) throws IOException {

    try (ServicesClient servicesClient = ServicesClient.create()) {
      LocationName parent = LocationName.of(projectId, location);
      for (Service service : servicesClient.listServices(parent).iterateAll()) {
        System.out.println("Service: " + service.getName());
        Timestamp ts = service.getCreateTime();
        Instant instant = Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
        System.out.println("Created at: " + instant.toString());


      }
    }
  }
}
// [END cloudrun_Services_ListServices_Locationname_sync]
