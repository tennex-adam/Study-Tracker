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

package io.studytracker.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "assay_task_fields")
@EntityListeners(AuditingEntityListener.class)
public class AssayTaskField extends CustomEntityField {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assay_task_id", nullable = false)
  private AssayTask assayTask;

//  public AssayTaskField() {
//    super();
//  }
//
//  public AssayTaskField(
//      AssayTask assayTask,
//      String displayName,
//      String fieldName,
//      CustomEntityFieldType type,
//      Integer order
//  ) {
//    super();
//    this.setAssayTask(assayTask);
//    this.setDisplayName(displayName);
//    this.setFieldName(fieldName);
//    this.setType(type);
//    this.setRequired(false);
//    this.setActive(true);
//    this.setFieldOrder(order);
//  }
//
//  public AssayTaskField(
//      AssayTask assayTask,
//      String displayName,
//      String fieldName,
//      CustomEntityFieldType type,
//      Integer order,
//      boolean required) {
//    this.setAssayTask(assayTask);
//    this.setDisplayName(displayName);
//    this.setFieldName(fieldName);
//    this.setType(type);
//    this.setFieldOrder(order);
//    this.setRequired(required);
//    this.setActive(true);
//  }
//
//  public AssayTaskField(
//      AssayTask assayTask,
//      String displayName,
//      String fieldName,
//      CustomEntityFieldType type,
//      Integer order,
//      boolean required,
//      String description) {
//    this.setAssayTask(assayTask);
//    this.setDisplayName(displayName);
//    this.setFieldName(fieldName);
//    this.setType(type);
//    this.setRequired(required);
//    this.setFieldOrder(order);
//    this.setActive(true);
//    this.setDescription(description);
//  }

  public AssayTask getAssayTask() {
    return assayTask;
  }

  public void setAssayTask(AssayTask assayTask) {
    this.assayTask = assayTask;
  }

  @Override
  public String toString() {
    return "AssayTaskField{" +
        "id=" + this.getId() +
        ", displayName='" + this.getDisplayName() + '\'' +
        ", fieldName='" + this.getFieldName() + '\'' +
        ", type=" + this.getType() +
        ", required=" + this.isRequired() +
        ", description='" + this.getDescription() + '\'' +
        ", active=" + this.isActive() +
        ", fieldOrder=" + this.getFieldOrder() +
        '}';
  }
}
