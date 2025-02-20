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

package io.studytracker.controller.api;

import io.studytracker.eln.NotebookEntryService;
import io.studytracker.events.util.StudyActivityUtils;
import io.studytracker.exception.RecordNotFoundException;
import io.studytracker.mapstruct.mapper.ActivityMapper;
import io.studytracker.mapstruct.mapper.AssayMapper;
import io.studytracker.mapstruct.mapper.CommentMapper;
import io.studytracker.mapstruct.mapper.StudyMapper;
import io.studytracker.model.Activity;
import io.studytracker.model.Assay;
import io.studytracker.model.Status;
import io.studytracker.model.Study;
import io.studytracker.model.StudyOptions;
import io.studytracker.service.AssayService;
import io.studytracker.service.CollaboratorService;
import io.studytracker.service.KeywordService;
import io.studytracker.service.ProgramService;
import io.studytracker.service.StudyCommentService;
import io.studytracker.service.StudyService;
import io.studytracker.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public abstract class AbstractStudyController extends AbstractApiController {

  private StudyService studyService;

  private UserService userService;

  private ProgramService programService;

  private AssayService assayService;

  private NotebookEntryService notebookEntryService;

  private StudyMapper studyMapper;

  private AssayMapper assayMapper;

  private ActivityMapper activityMapper;

  private CollaboratorService collaboratorService;

  private KeywordService keywordService;

  private StudyCommentService studyCommentService;

  private CommentMapper commentMapper;

  private boolean isLong(String value) {
    try {
      Long.parseLong(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  protected Study getStudyFromIdentifier(String id) {
    Optional<Study> optional;
    if (isLong(id)) {
      optional = studyService.findById(Long.parseLong(id));
    } else {
      optional = studyService.findByCode(id);
    }
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new RecordNotFoundException("Cannot find study: " + id);
    }
  }

  protected Assay getAssayFromIdentifier(String id) {
    Optional<Assay> optional;
    if (isLong(id)) {
      optional = assayService.findById(Long.parseLong(id));
    } else {
      optional = assayService.findByCode(id);
    }
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new RecordNotFoundException("Cannot find assay: " + id);
    }
  }

  /**
   * Creates a new study with notebook and storage folders, where appropriate.
   * @param study the study to create
   * @param options study creation options
   * @return
   */
  protected Study createNewStudy(Study study, StudyOptions options) {
    Study created = studyService.create(study, options);
    Assert.notNull(created.getId(), "Study not persisted.");
    Activity activity = StudyActivityUtils.fromNewStudy(created, this.getAuthenticatedUser());
    this.logActivity(activity);
    return created;
  }

  /**
   * Updates an existing study.
   *
   * @param study the study to update
   * @return the updated study
   */
  protected Study updateExistingStudy(Study study) {
    Study updated = studyService.update(study);
    Activity activity = StudyActivityUtils.fromUpdatedStudy(updated, this.getAuthenticatedUser());
    this.logActivity(activity);
    return updated;
  }

  /**
   * Innactivates an existing study.
   *
   * @param study the study to inactivate
   */
  protected void deleteExistingStudy(Study study) {
    studyService.delete(study);
    Activity activity = StudyActivityUtils.fromDeletedStudy(study, this.getAuthenticatedUser());
    this.logActivity(activity);
  }

  protected void updateExistingStudyStatus(Study study, Status status) {
    Status oldStatus = study.getStatus();
    studyService.updateStatus(study, status);
    Activity activity = StudyActivityUtils.fromStudyStatusChange(study, this.getAuthenticatedUser(), oldStatus, status);
    this.logActivity(activity);
  }

  protected void updateExistingStudyStatus(Study study, String statusString) {
    Status status = Status.valueOf(statusString);
    this.updateExistingStudyStatus(study, status);
  }

  protected void restoreRemovedStudy(Study study) {
    studyService.restore(study);
    Activity activity = StudyActivityUtils.fromRestoredStudy(study, this.getAuthenticatedUser());
    this.logActivity(activity);
  }

  public StudyService getStudyService() {
    return studyService;
  }

  @Autowired
  public void setStudyService(StudyService studyService) {
    this.studyService = studyService;
  }

  public UserService getUserService() {
    return userService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public ProgramService getProgramService() {
    return programService;
  }

  @Autowired
  public void setProgramService(ProgramService programService) {
    this.programService = programService;
  }

  public AssayService getAssayService() {
    return assayService;
  }

  @Autowired
  public void setAssayService(AssayService assayService) {
    this.assayService = assayService;
  }

  public StudyMapper getStudyMapper() {
    return studyMapper;
  }

  @Autowired
  public void setStudyMapper(StudyMapper studyMapper) {
    this.studyMapper = studyMapper;
  }

  public AssayMapper getAssayMapper() {
    return assayMapper;
  }

  @Autowired
  public void setAssayMapper(AssayMapper assayMapper) {
    this.assayMapper = assayMapper;
  }

  public ActivityMapper getActivityMapper() {
    return activityMapper;
  }

  @Autowired
  public void setActivityMapper(ActivityMapper activityMapper) {
    this.activityMapper = activityMapper;
  }

  public NotebookEntryService getNotebookEntryService() {
    return notebookEntryService;
  }

  @Autowired(required = false)
  public void setNotebookEntryService(NotebookEntryService notebookEntryService) {
    this.notebookEntryService = notebookEntryService;
  }

  public CollaboratorService getCollaboratorService() {
    return collaboratorService;
  }

  @Autowired
  public void setCollaboratorService(CollaboratorService collaboratorService) {
    this.collaboratorService = collaboratorService;
  }

  public KeywordService getKeywordService() {
    return keywordService;
  }

  @Autowired
  public void setKeywordService(KeywordService keywordService) {
    this.keywordService = keywordService;
  }

  public StudyCommentService getStudyCommentService() {
    return studyCommentService;
  }

  @Autowired
  public void setStudyCommentService(StudyCommentService studyCommentService) {
    this.studyCommentService = studyCommentService;
  }

  public CommentMapper getCommentMapper() {
    return commentMapper;
  }

  @Autowired
  public void setCommentMapper(CommentMapper commentMapper) {
    this.commentMapper = commentMapper;
  }
}
