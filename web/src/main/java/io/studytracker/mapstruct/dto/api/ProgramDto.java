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

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Data;

@Data
public class ProgramDto {

  private Long id;
  private Long organizationId;
  private String code;
  private String name;
  private String description;
  private Long createdBy;
  private Long lastModifiedBy;
  private Date createdAt;
  private Date updatedAt;
  private boolean active;
  private Long notebookFolderId;
  private Set<Long> storageFolders = new HashSet<>();
  private Map<String, String> attributes = new HashMap<>();
}
