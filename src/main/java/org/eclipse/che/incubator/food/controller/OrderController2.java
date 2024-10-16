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
package org.eclipse.che.incubator.food.controller;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.che.incubator.food.entity.OrderList;
import org.eclipse.che.incubator.food.exception.CustomException;
import org.eclipse.che.incubator.food.repository.OrderListRepository;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import io.quarkus.panache.common.Sort;

@Path("/orderList2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController2 {
	
	@Inject
	private OrderListRepository orderListRepository;

	// 查詢所有訂單結果
	@GET
	public List<OrderList> getAll() {
		return orderListRepository.listAll();
	}

	// 查詢所有訂單，且升序
	@GET
	@Path("/orderByTradeDate")
	public List<OrderList> getAllAsc() {
		return orderListRepository.listAll(Sort.by("trade_date").ascending());
	}

	// 用id查詢訂單
	@POST
	@Path("/getById")
	public OrderList getById(@RequestBody OrderList orderList) {
		return orderListRepository.findById(orderList.id);
	}

	// 用訂單號碼查詢訂單
	@POST
	@Path("/getByOrderNo")
	public OrderList findByOrderNo(@RequestBody OrderList orderList) {
		return orderListRepository.findByOrderNo(orderList);
	}

	// 用交易日期查詢所有訂單
	@POST
	@Path("/listByTradeDate")
	public List<OrderList> listByTradeDate(@RequestBody OrderList orderList) {
		return orderListRepository.listByTradeDate(orderList);
	}

	// 查詢訂單及明細
	@POST
	@Path("/getOrdersAndDetails")
	public List<OrderList> getOrdersAndDetails(@RequestBody OrderList orderList) {
		return orderListRepository.getOrdersAndDetails(orderList);
	}
	
	// 新增訂單
	@POST
	@Path("/add")
	@Transactional
	public Response add(@RequestBody OrderList orderList) {
		if (orderList.id != null) {
			throw new CustomException("ID不可有值");
		}
		orderListRepository.persist(orderList);
		if (orderListRepository.isPersistent(orderList)) {
			return Response.created(URI.create("/orderList/" + orderList.id)).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	// 修改訂單
	@POST
	@Path("/update1")
	@Transactional
	public Response update1(@RequestBody OrderList orderList) {
		orderListRepository.updateByOrderList(orderList);
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	
	// 刪除訂單
	@POST
	@Path("/delete")
	@Transactional
	public void delete(@RequestBody OrderList orderList) {
		orderListRepository.deleteById(orderList.id);
	}

}
