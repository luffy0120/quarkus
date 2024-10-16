/*
 * Copyright (c) 2022 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.incubator.food.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.eclipse.che.incubator.food.dto.FoodPkeys;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "food")
@IdClass(FoodPkeys.class)
public class Food extends PanacheEntityBase {
	
	@Id
	public Long id;
	
	@Id
    public String name;

    public String restaurantName;

    public double price;

}