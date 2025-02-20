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

package io.studytracker.config.initialization;

import io.studytracker.model.Organization;
import io.studytracker.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrganizationInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOrganizationInitializer.class);

  @Autowired
  private OrganizationRepository organizationRepository;

  public Organization initializeDefaultOrganization() {
    if (organizationRepository.count() > 0) {
      LOGGER.info("Organizations already defined. Skipping default organization initialization.");
      return null;
    }
    LOGGER.info("No organizations defined. Initializing default organization...");
    Organization organization = new Organization();
    organization.setName("My Organization");
    organization.setDescription("Default organization");
    organization.setActive(true);
    return organizationRepository.save(organization);
  }

}
