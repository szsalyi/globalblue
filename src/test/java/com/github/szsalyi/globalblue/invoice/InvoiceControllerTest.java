package com.github.szsalyi.globalblue.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.szsalyi.globalblue.dto.InvoiceDetails;
import com.github.szsalyi.globalblue.math.ScaledBigDecimal;
import com.github.szsalyi.globalblue.spring.GlobalBlueApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        GlobalBlueApplication.class,
        JacksonAutoConfiguration.class
}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    InvoiceController controller;

    @Mock
    InvoiceService invoiceService;

    @BeforeEach
    void setup() {
        when(invoiceService.calculate(any(), any(), any())).thenReturn(new InvoiceDetails(
                new ScaledBigDecimal(new BigDecimal(200)),
                new ScaledBigDecimal(new BigDecimal(200)),
                new ScaledBigDecimal(new BigDecimal(200))
        ));
        this.controller = new InvoiceController(invoiceService);
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void givenValidParams_whenGetInvoiceDetail_thenReturnOk() throws Exception {
        InvoiceDetails invoiceDetails = new InvoiceDetails(
                new ScaledBigDecimal(new BigDecimal("500.00")),
                new ScaledBigDecimal(new BigDecimal("600.00")),
                new ScaledBigDecimal(new BigDecimal("100")));

        this.mockMvc.perform(
                        get("/api/v1/invoice/detail")
                                .param("amount", "100")
                                .param("type", "VAT")
                                .param("vatRate", "20"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(invoiceDetails)))
                .andDo(print());
    }

    @Test
    void givenInvalidInputType_whenGetInvoiceDetail_thenReturnOk() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/invoice/detail")
                                .param("amount", "100")
                                .param("type", "V")
                                .param("vatRate", "20"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenInvalidDigitsAmount_whenGetInvoiceDetail_thenReturnOk() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/invoice/detail")
                                .param("amount", "100.12")
                                .param("type", "V")
                                .param("vatRate", "20"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenMissingVatRate_whenGetInvoiceDetail_thenReturnOk() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/invoice/detail")
                                .param("amount", "100.12")
                                .param("type", "VAT"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}

