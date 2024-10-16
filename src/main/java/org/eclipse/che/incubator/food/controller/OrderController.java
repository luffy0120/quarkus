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

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.che.incubator.food.entity.OrderDetail;
import org.eclipse.che.incubator.food.entity.OrderList;
import org.eclipse.che.incubator.food.exception.CustomException;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import io.quarkus.panache.common.Sort;

@Path("/orderList")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

	// 查詢所有訂單結果
	@GET
	public List<OrderList> getAll() {
		return OrderList.listAll();
	}

	// 查詢所有訂單，且升序
	@GET
	@Path("/orderByTradeDate")
	public List<OrderList> getAllAsc() {
		return OrderList.listAll(Sort.by("trade_date").ascending());
	}

	// 用id查詢訂單
	@POST
	@Path("/getById")
	public OrderList getById(@RequestBody OrderList orderList) {
		return OrderList.findById(orderList.id);
	}

	// 用訂單號碼查詢訂單
	@POST
	@Path("/getByOrderNo")
	public OrderList findByOrderNo(@RequestBody OrderList orderList) {
		return OrderList.find("order_no", orderList.orderNo).firstResult();
	}

	// 用交易日期查詢所有訂單
	@POST
	@Path("/listByTradeDate")
	public List<OrderDetail> listByTradeDate(@RequestBody OrderList orderList) {
		return OrderList.find("trade_date", Sort.by("trade_date"), orderList.tradeDate).list();
	}

	// 查詢訂單及明細
	@POST
	@Path("/getOrdersAndDetails")
	public List<OrderList> getOrdersAndDetails(@RequestBody OrderList orderList) {
		return OrderList.find("from OrderList ol left join OrderDetail od on ol.orderNo = od.orderNo where ol.id = ?1", orderList.id).list();
	}
	
	// 新增訂單
	@POST
	@Path("/add")
	@Transactional
	public Response add(@RequestBody OrderList orderList) {
		if (orderList.id != null) {
			throw new CustomException("ID不可有值");
		}
		orderList.persist();
		if (orderList.isPersistent()) {
			return Response.created(URI.create("/orderList/" + orderList.id)).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	// 修改訂單-方法一
	@POST
	@Path("/update1")
	@Transactional
	public Response update1(@RequestBody OrderList orderList) {
		orderList.update("order_no = ?1, buyer = ?2 where id = ?3", orderList.orderNo, orderList.buyer, orderList.id);
		if (orderList.isPersistent()) {
			return Response.created(URI.create("/orderList/" + orderList.orderNo)).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
	
	// 修改訂單- 方法二
	@POST
	@Path("/update2")
	@Transactional
	public Response update2(@RequestBody OrderList orderList) {
		OrderList ol = OrderList.findById(orderList.id);
		ol.buyer = orderList.buyer;
		ol.tradeDate = orderList.tradeDate;		
		if (orderList.isPersistent()) {
			return Response.created(URI.create("/orderList/" + orderList.orderNo)).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	// 刪除訂單
	@POST
	@Path("/delete")
	@Transactional
	public void delete(@RequestBody OrderList orderList) {
		orderList.delete("id", orderList.id);
	}

}
