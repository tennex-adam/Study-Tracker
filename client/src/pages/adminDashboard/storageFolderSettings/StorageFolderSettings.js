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

import React, {useContext, useEffect, useRef, useState} from "react";
import axios from "axios";
import {Button, Card, Col, Dropdown, Row} from "react-bootstrap";
import {FolderPlus} from "react-feather";
import {SettingsLoadingMessage} from "../../../common/loading";
import {SettingsErrorMessage} from "../../../common/errors";
import StorageFolderFormModal from "./StorageFolderFormModal";
import NotyfContext from "../../../context/NotyfContext";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import StorageFolderCard from "../../../common/fileManager/StorageFolderCard";
import StorageFoldersPlaceholder from "./StorageFoldersPlaceholder";
import {faFilter} from "@fortawesome/free-solid-svg-icons";
import swal from "sweetalert";

const StorageFolderSettings = () => {

  const notyf = useContext(NotyfContext);

  const [folders, setFolders] = useState(null);
  const [loadCounter, setLoadCounter] = useState(0);
  const [showModal, setShowModal] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedFolder, setSelectedFolder] = useState(null);
  const [filter, setFilter] = useState("SHOW_ALL");
  const formikRef = useRef();

  const filterLabels = {
    SHOW_ALL: "Show All",
    STUDY_ROOT: "Study Root Only",
    BROWSER_ROOT: "Browser Root Only"
  }

  useEffect(() => {
    setIsLoading(true);
    axios.get("/api/internal/storage-drive-folders?root=true")
    .then(async response => {
      console.debug("Storage folders", response.data);
      setFolders(response.data);
    })
    .catch(e => {
      console.error(e);
      setError(e)
      notyf.open({message: 'Failed to load available storage folders.', type: 'error'});
    })
    .finally(() => {
      setIsLoading(false);
    });
  }, [loadCounter, notyf]);

  const handleSubmitForm = (values, {setSubmitting, resetForm}) => {
    const url = selectedFolder
        ? "/api/internal/storage-drive-folders/" + selectedFolder.id
        : "/api/internal/storage-drive-folders/";
    const method = selectedFolder ? "PUT" : "POST";
    axios({
      method: method,
      url: url,
      data: values,
      headers: {
        "Content-Type": "application/json"
      },
    })
    .then(response => {
      notyf.open({message: 'Storage folder saved.', type: 'success'});
      resetForm();
      setShowModal(false);
    })
    .catch(e => {
      console.error(e);
      if (e.response.status === 404) {
        notyf.open({message: 'The requested folder does not exist: ' + values.rootFolderPath, type: 'error'});
      } else {
        notyf.open({message: 'Failed to save storage location.', type: 'error'});
      }
    })
    .finally(() => {
      setSubmitting(false);
      setLoadCounter(loadCounter + 1);
    })
  }

  const handleFolderEdit = (folder) => {
    console.debug("Edit folder: ", folder);
    formikRef.current?.resetForm();
    setSelectedFolder(folder);
    setShowModal(true);
  }

  const handleFolderDelete = (folder) => {
    swal({
      title: "Are you sure you want to delete this folder?",
      text: "Removing root folders will not delete the folder in the file system or "
          + "affect any subfolders created from this folder. You can re-register this "
          + "folder at any time to continue using the existing folder in the file system.",
      icon: "warning",
      buttons: true
    })
    .then(val => {
      if (val) {
        axios.delete("/api/internal/storage-drive-folders/" + folder.id)
        .then(response => {
          setLoadCounter(loadCounter + 1);
          notyf.open({message: 'Storage folder deleted.', type: 'success'})
        })
        .catch(error => {
          console.error(error);
          notyf.open({message: 'Failed to delete storage folder.', type: 'error'});
        })
      }
    });
  }

  const handleFilterChange = (filter) => {
    console.debug("Filter change: ", filter);
    setFilter(filter);
  }

  return (
      <>

        <Row className={"mb-3 justify-content-around"}>

          <Col>
            <h3>Storage Folders</h3>
          </Col>

          <Col>
            <div className="d-flex justify-content-end">

              <span>
                <Dropdown className="me-2 mb-1">
                  <Dropdown.Toggle variant={"outline-info"}>
                    <FontAwesomeIcon icon={faFilter} className={"me-2"}/>
                    {filterLabels[filter]}
                  </Dropdown.Toggle>
                  <Dropdown.Menu>

                    <Dropdown.Item onClick={() => handleFilterChange("SHOW_ALL")}>
                      {filterLabels["SHOW_ALL"]}
                    </Dropdown.Item>

                    <Dropdown.Item onClick={() => handleFilterChange("STUDY_ROOT")}>
                      {filterLabels["STUDY_ROOT"]}
                    </Dropdown.Item>

                    <Dropdown.Item onClick={() => handleFilterChange("BROWSER_ROOT")}>
                      {filterLabels["BROWSER_ROOT"]}
                    </Dropdown.Item>

                  </Dropdown.Menu>
                </Dropdown>
              </span>

              <span>
                <Button
                    variant={"primary"}
                    onClick={() => {
                      formikRef.current?.resetForm();
                      setSelectedFolder(null);
                      setShowModal(true);
                    }}
                >
                  <FolderPlus className="feather align-middle me-2 mb-1"/>
                  Add Folder
                </Button>
              </span>

            </div>
          </Col>
        </Row>

        <Row>
          <Col>
            <Card className={"illustration"}>
              <Card.Body>
                <Row>
                  <Col>
                    Root folders are folders in local file systems or cloud services that Study Tracker
                    users can use to store their data. A root study folder will be created on
                    application startup, dependent on the <code>storage.mode</code> setting used, which
                    will determine where program, study, and assay folders are created by default. You
                    can add additional root folders here from the available configured storage systems.
                  </Col>
                </Row>
              </Card.Body>
            </Card>
          </Col>
        </Row>

        {
            isLoading && !error && <SettingsLoadingMessage />
        }

        {
            error && <SettingsErrorMessage />
        }

        {
            folders && folders.length > 0 && (
                folders
                .filter(folder => {
                  if (filter === "SHOW_ALL") {
                    return true;
                  } else if (filter === "STUDY_ROOT") {
                    return folder.studyRoot;
                  } else {
                    return folder.browserRoot;
                  }
                })
                .map((folder, index) => (
                    <Row key={"storage-folder-card-" + index}>
                      <Col>
                        <StorageFolderCard
                            folder={folder}
                            handleFolderEdit={handleFolderEdit}
                            handleFolderDelete={handleFolderDelete}
                        />
                      </Col>
                    </Row>
                )
            ))
        }

        {
          (!folders || folders.length === 0) && (
              <Row>
                <Col>
                  <StorageFoldersPlaceholder handleClick={() => {
                    formikRef.current?.resetForm();
                    setSelectedFolder(null);
                    setShowModal(true);
                  }} />
                </Col>
              </Row>
          )
        }

        <StorageFolderFormModal
            isOpen={showModal}
            setIsOpen={setShowModal}
            selectedFolder={selectedFolder}
            handleFormSubmit={handleSubmitForm}
            formikRef={formikRef}
        />

      </>

  )

}

export default StorageFolderSettings;