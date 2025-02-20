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

package io.studytracker.mapstruct.dto.api;

import io.studytracker.model.Status;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Data;

@Data
public class StudyDto {

  private Long id;
  private String code;
  private String externalCode;
  private Status status;
  private String name;
  private Long programId;
  private String description;
  private Long collaboratorId;
  private boolean legacy = false;
  private boolean active = true;
  private Long notebookFolderId;
  private Long storageFolderId;
  private Long createdBy;
  private Long lastModifiedBy;
  private Date startDate;
  private Date endDate;
  private Date createdAt;
  private Date updatedAt;
  private Long owner;
  private Set<Long> users = new HashSet<>();
  private Set<Long> keywords = new HashSet<>();
  private Map<String, String> attributes = new HashMap<>();
  private Set<Long> externalLinks = new HashSet<>();
  private Set<Long> studyRelationships = new HashSet<>();
  private Long conclusionsId;
  private Set<Long> comments = new HashSet<>();
}
