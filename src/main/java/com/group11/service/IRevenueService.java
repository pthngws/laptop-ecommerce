package com.group11.service;

import java.time.LocalDateTime;

public interface IRevenueService {
    public long calculateDailyRevenue();
    public long calculateWeeklyRevenue();
    public long calculateMonthlyRevenue();
    public long calculateYearlyRevenue();
    public long calculateTotalRevenue();
    public long calculateRevenue(LocalDateTime startDate, LocalDateTime endDate);
    public double[] calculateMonthlyRevenueForYear();
    //public List<OrderEntity> getOrdersWithShippingStatusAndReceiveDate(LocalDateTime startDate, LocalDateTime endDate);
}
