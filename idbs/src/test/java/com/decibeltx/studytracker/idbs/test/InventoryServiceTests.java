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

package com.decibeltx.studytracker.idbs.test;

import com.decibeltx.studytracker.idbs.inventory.IdbsInventoryService;
import com.decibeltx.studytracker.idbs.inventory.models.InventoryCategory;
import com.decibeltx.studytracker.idbs.inventory.models.InventoryObject;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles({"example"})
public class InventoryServiceTests {

  @Autowired
  private IdbsInventoryService inventoryService;

  @Test
  public void inventorySearchTest() throws Exception {
    List<InventoryObject> items = inventoryService
        .findInventoryItemsByType(InventoryCategory.VIRUS);
    Assert.assertNotNull(items);
    Assert.assertFalse(items.isEmpty());
  }

}
