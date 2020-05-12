/*
 * Copyright 2020 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from "react";
import {Col, Row} from 'reactstrap'
import {ActivityTable} from "../activity";

class StudyActivityTab extends React.Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {

    return (
        <div className="activity-tab">

          <Row className="justify-content-between align-items-center">
            <Col sm={12}>
              <h4 className={"mb-4"}>Study Activity</h4>
            </Col>
          </Row>

          <Row>
            <Col sm={12}>
              <ActivityTable activity={this.props.study.activity}/>
            </Col>
          </Row>

        </div>
    )

  }

}

export default StudyActivityTab;