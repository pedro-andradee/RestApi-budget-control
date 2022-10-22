package com.pedro.andrade.finance.control.services;

import com.pedro.andrade.finance.control.dto.ReportDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReportServiceIntegTest {

    @Autowired
    private ReportService reportService;

    private int year;
    private int month;

    @BeforeEach
    void setUp() {
        year = 2022;
        month = 06;
    }

    @Test
    void getReportByYearAndMonthShouldReturnReportDTO() {
        ReportDTO result = reportService.getReportByYearAndMonth(year, month);
        Assertions.assertNotNull(result);
    }
}