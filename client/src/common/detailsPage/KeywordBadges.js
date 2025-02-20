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

import React from "react";
import PropTypes from "prop-types";
import {Badge} from "react-bootstrap";

const KeywordBadges = ({keywords}) => {

  const badges = keywords.map((keyword, i) => {
    return (
        <Badge key={i} bg={"info"} className={"me-2 mb-2 keyword-badge"}>
          {keyword.category.name}: {keyword.keyword}
        </Badge>
    )
  })

  return (
      <div>{badges}</div>
  )
}

KeywordBadges.propTypes = {
  keywords: PropTypes.array.isRequired
}

export default KeywordBadges;