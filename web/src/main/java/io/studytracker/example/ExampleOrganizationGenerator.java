/*
 * Copyright 2019-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.studytracker.example;

import io.studytracker.model.Organization;
import io.studytracker.repository.OrganizationRepository;
import java.util.List;

public class ExampleOrganizationGenerator implements ExampleDataGenerator<Organization> {

  public static final int ORGANIZATION_COUNT = 1;

  private final OrganizationRepository organizationRepository;

  public ExampleOrganizationGenerator(OrganizationRepository organizationRepository) {
    this.organizationRepository = organizationRepository;
  }

  @Override
  public List<Organization> generateData(Object... args) {
    List<Organization> organizations = organizationRepository.findAll();
    if (organizations.isEmpty()) {
      Organization organization = new Organization();
      organization.setName("My Organization");
      organization.setActive(true);
      organization.setDescription("Example Organization");
      organizationRepository.save(organization);
      organizations.add(organization);
    }
    return organizations;
  }

  @Override
  public void deleteData() {
    System.out.println("Example organization will not be deleted");
  }
}
