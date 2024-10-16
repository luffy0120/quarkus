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
package org.eclipse.che.incubator.food.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;
@RegisterForReflection 
public class OrderListAndProductDto {

	public Integer id;

	public String orderNo;

	public String buyer;

	public LocalDate tradeDate;

	public BigDecimal totalPrice;

	public OrderListAndProductDto(Integer id, String orderNo, String buyer, LocalDate tradeDate,
			BigDecimal totalPrice) {
		this.id = id;
		this.orderNo = orderNo;
		this.buyer = buyer;
		this.tradeDate = tradeDate;
		this.totalPrice = totalPrice;
	}

}