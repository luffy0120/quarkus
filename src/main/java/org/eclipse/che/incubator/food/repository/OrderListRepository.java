package org.eclipse.che.incubator.food.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.che.incubator.food.entity.OrderList;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class OrderListRepository implements PanacheRepository<OrderList>{
	
	public OrderList findByOrderNo(OrderList orderList) {
		return find("order_no", orderList.orderNo).firstResult();
	}
	
	public List<OrderList> listByTradeDate(OrderList orderList) {
		return find("trade_date", Sort.by("trade_date"), orderList.tradeDate).list();
	}
	
	public List<OrderList> getOrdersAndDetails(OrderList orderList) {
		return find("from OrderList ol left join OrderDetail od on ol.orderNo = od.orderNo where ol.id = ?1", orderList.id).list();
	}
	
	public void updateByOrderList(OrderList orderList) {
		update("order_no = ?1, buyer = ?2 where id = ?3", orderList.orderNo, orderList.buyer, orderList.id);
	}
	
}
